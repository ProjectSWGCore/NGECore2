/*******************************************************************************
 * Copyright (c) 2013 <Project SWG>
 * 
 * This File is part of NGECore2.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Using NGEngine to work with NGECore2 is making a combined work based on NGEngine. 
 * Therefore all terms and conditions of the GNU Lesser General Public License cover the combination.
 ******************************************************************************/
package services.command;

import java.nio.ByteOrder;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import resources.common.*;
import protocol.swg.ObjControllerMessage;
import protocol.swg.objectControllerObjects.CommandEnqueue;
import protocol.swg.objectControllerObjects.CommandEnqueueRemove;
import protocol.swg.objectControllerObjects.ShowFlyText;
import protocol.swg.objectControllerObjects.StartTask;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;

public class CommandService implements INetworkDispatch  {
	
	private Vector<BaseSWGCommand> commandLookup = new Vector<BaseSWGCommand>();
	private ConcurrentHashMap<String,BaseSWGCommand> aliases = new ConcurrentHashMap<String,BaseSWGCommand>();
	private ConcurrentHashMap<Integer,BaseSWGCommand> aliasesByCRC = new ConcurrentHashMap<Integer,BaseSWGCommand>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private NGECore core;
	
	public CommandService(NGECore core) {
		this.core = core;
	}


	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
		objControllerOpcodes.put(ObjControllerOpcodes.COMMAND_QUEUE_ENQUEUE, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				Client client = core.getClient(session);

				if(client == null) {
					System.out.println("NULL Client");
					return;
				}

				CommandEnqueue commandEnqueue = new CommandEnqueue();
				commandEnqueue.deserialize(data);
				
				
				BaseSWGCommand command = getCommandByCRC(commandEnqueue.getCommandCRC());
				
				if(command == null) {
					//System.out.println("Unknown Command CRC: " + commandEnqueue.getCommandCRC());
					return;
				}
				
				// TODO: command filters for state, posture etc.
				
				if(client.getParent() == null) {
					System.out.println("NULL Object");
					return;
				}
				
				if(command.isGmCommand() && !client.isGM())
					return;

				CreatureObject actor = (CreatureObject) client.getParent();

				if (actor.hasCooldown(command.getCommandName()))
					return;
				
				SWGObject target = core.objectService.getObject(commandEnqueue.getTargetID());
				
				// May want to have a warmup def to be called at some point in the future.
				if (command.getWarmupTime() != 0 && !(command instanceof CombatCommand)) {
					scheduler.schedule(new Runnable() {

						@Override
						public void run() {
							core.scriptService.callScript("scripts/commands/", command.getCommandName(), "run", core, actor, target, commandEnqueue.getCommandArguments());
						}
					}, (long) command.getWarmupTime(), TimeUnit.SECONDS);
				} else {

					if(command instanceof CombatCommand) {
						CombatCommand command2 = (CombatCommand) command.clone();
						processCombatCommand(actor, target, command2, commandEnqueue.getActionCounter(), commandEnqueue.getCommandArguments());
						return;
					}

					core.scriptService.callScript("scripts/commands/", command.getCommandName(), "run", core, actor, target, commandEnqueue.getCommandArguments());
				}
			}

		});
		
		objControllerOpcodes.put(ObjControllerOpcodes.COMMAND_QUEUE_REMOVE, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
			}
			
		});
			
		
	}
	
	public BaseSWGCommand registerCommand(String name) {
		
		BaseSWGCommand command = new BaseSWGCommand(name.toLowerCase());
		commandLookup.add(command);
		return command;
		
	}

	public CombatCommand registerCombatCommand(String name) {
		
		CombatCommand command = new CombatCommand(name.toLowerCase());
		commandLookup.add(command);
		return command;
		
	}
	
	public BaseSWGCommand registerGmCommand(String name) {
		
		BaseSWGCommand command = new BaseSWGCommand(name.toLowerCase());
		command.setGmCommand(true);
		commandLookup.add(command);
		return command;
		
	}
	
	public void registerAlias(String name, String target) {
		Vector<BaseSWGCommand> commands = new Vector<BaseSWGCommand>(commandLookup); 	// copy for thread safety
		BaseSWGCommand targetCommand = null;
		for(BaseSWGCommand command : commands) {
			if(command.getCommandName().equalsIgnoreCase(target)) {
				targetCommand = command;
			}
		}
		if (targetCommand == null) { return; }
		
		aliases.put(name, targetCommand);
		aliasesByCRC.put(CRC.StringtoCRC(name), targetCommand);
		
	}

	public BaseSWGCommand getCommandByCRC(int CRC) {
		
		if (aliasesByCRC.containsKey(CRC)) {
			return aliasesByCRC.get(CRC);
		}
		
		Vector<BaseSWGCommand> commands = new Vector<BaseSWGCommand>(commandLookup); 	// copy for thread safety
		
		for(BaseSWGCommand command : commands) {
			if(command.getCommandCRC() == CRC)
				return command;
		}
		return null;

	}
	
	public BaseSWGCommand getCommandByName(String name) {
		
		if (aliases.containsKey(name)) {
			return aliases.get(name);
		}
		
		Vector<BaseSWGCommand> commands = new Vector<BaseSWGCommand>(commandLookup); 	// copy for thread safety
		
		for(BaseSWGCommand command : commands) {
			if(command.getCommandName().equalsIgnoreCase(name))
				return command;
		}
		return null;

	}
	
	public void processCombatCommand(CreatureObject attacker, SWGObject target, CombatCommand command, int actionCounter, String commandArgs) {
		
		// Check if the person has access to this ability.
		// Abilities (inc expertise ones) are added automatically as they level
		// by reading the datatables.
		// disabled for now (breaks all combat)
		//if (!attacker.hasAbility(command.getCommandName())) {
	//		return;
		//}
		
		if(FileUtilities.doesFileExist("scripts/commands/combat/" + command.getCommandName() + ".py"))
			core.scriptService.callScript("scripts/commands/combat/", command.getCommandName(), "setup", core, attacker, target, command);
		
		boolean success = true;
		
		//if((command.getHitType() == 5 || command.getHitType() == 7) && !(target instanceof CreatureObject))
		//	success = false;
		
		if(!(command.getAttackType() == 2) && !(command.getHitType() == 5) && !(command.getHitType() == 0)) {
			if(target == null || !(target instanceof TangibleObject) || target == attacker)
				success = false;
		} else {
			if(target == null)
				target = attacker;
			else if(!(target instanceof TangibleObject))
				return;
		}
		
		if(attacker.getPosture() == 13 || attacker.getPosture() == 14)
				success = false;
		
		if(target instanceof CreatureObject) {
			if(!(command.getHitType() == 5))
				if(((CreatureObject) target).getPosture() == 13)
					success = false;
			if(!(command.getHitType() == 7))
				if(((CreatureObject) target).getPosture() == 14)
					success = false;
		}
		
		if(command.getHitType() == 7 && target.getClient() == null)
			success = false;
		
		WeaponObject weapon;
		
		if(attacker.getWeaponId() == 0)
			weapon = (WeaponObject) attacker.getSlottedObject("default_weapon");	// use unarmed/default weapon if no weapon is equipped
		else
			weapon = (WeaponObject) core.objectService.getObject(attacker.getWeaponId());
		
		float maxRange = 0;
		
		if(command.getMaxRange() == 0)
			maxRange = weapon.getMaxRange();
		else
			maxRange = command.getMaxRange();
				
		Point3D attackerPos = attacker.getWorldPosition();
		Point3D defenderPos = attacker.getWorldPosition();
		
		if(attackerPos.getDistance(defenderPos) > maxRange && maxRange != 0)
			success = false;
		
		if(command.getMinRange() > 0) {
			if(attackerPos.getDistance(defenderPos) < command.getMinRange())
				success = false;
		}
		
		
		if(target != attacker && success && !core.simulationService.checkLineOfSight(attacker, target)) {
			
			ShowFlyText los = new ShowFlyText(attacker.getObjectID(), attacker.getObjectID(), "combat_effects", "cant_see", (float) 1.5, new RGB(72, 209, 204), 1);
			ObjControllerMessage objController = new ObjControllerMessage(0x1B, los);
			attacker.getClient().getSession().write(objController.serialize());
			success = false;
			
		}
		
		if(!success && attacker.getClient() != null) {
			IoSession session = attacker.getClient().getSession();
			CommandEnqueueRemove commandRemove = new CommandEnqueueRemove(attacker.getObjectId(), actionCounter);
			session.write(new ObjControllerMessage(0x0B, commandRemove).serialize());
			StartTask startTask = new StartTask(actionCounter, attacker.getObjectID(), command.getCommandCRC(), CRC.StringtoCRC(command.getCooldownGroup()), -1);
			session.write(new ObjControllerMessage(0x0B, startTask).serialize());
		} else {
			
			if(command.getHitType() == 5) {
				core.combatService.doHeal(attacker, (CreatureObject) target, weapon, command, actionCounter);
				return;
			}
			
			if(command.getHitType() == 7) {
				core.combatService.doRevive(attacker, (CreatureObject) target, weapon, command, actionCounter);
				return;
			}
			
			if(command.getHitType() == 0 && command.getBuffNameSelf().length() > 0) {
				core.combatService.doSelfBuff(attacker, weapon, command, actionCounter);
				return;
			}
			for(int i = 0 ; i < command.getAttack_rolls(); i++) {
				core.combatService.doCombat(attacker, (TangibleObject) target, weapon, command, actionCounter);
			}
			
		}
		
	}

	public void callCommand(SWGObject actor, String commandName, SWGObject target, String commandArgs) {
		if (actor == null)
			return;
		
		BaseSWGCommand command = getCommandByName(commandName);
		
		if (command == null)
			return;
		
		if(command instanceof CombatCommand) {
			CombatCommand command2;
			try {
				command2 = (CombatCommand) command.clone();
				processCombatCommand((CreatureObject) actor, target, command2, 0, "");
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			return;
		}
		
		core.scriptService.callScript("scripts/commands/", command.getCommandName(), "run", core, actor, target, commandArgs);
	}
	
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
