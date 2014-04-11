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

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
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
import resources.objects.harvester.HarvesterObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;

public class CommandService implements INetworkDispatch  {
	
	private Vector<BaseSWGCommand> commandLookup = new Vector<BaseSWGCommand>();
	private ConcurrentHashMap<Integer, Integer> aliases = new ConcurrentHashMap<Integer, Integer>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private NGECore core;
	
	public CommandService(NGECore core) {
		this.core = core;
	}
	
	public void registerAlias(String name, String target) {
		aliases.put(CRC.StringtoCRC(name.toLowerCase()), CRC.StringtoCRC(target.toLowerCase()));
	}
	
	public boolean callCommand(CreatureObject actor, SWGObject target, BaseSWGCommand command, int actionCounter, String commandArgs) {
		if (actor == null) {
			return false;
		}
		
		if (command == null) {
			return false;
		}
		
		if (command.getCharacterAbility().length() > 0 && !actor.hasAbility(command.getCharacterAbility())) {
			return false;
		}
		
		if (command.isDisabled()) {
			return false;
		}
		
		if (command.getGodLevel() > 0 && (actor.getClient() == null || !actor.getClient().isGM())) {
			return false;
		}
		
		if (actor.hasCooldown(command.getCooldownGroup()) || actor.hasCooldown(command.getCommandName())) {
			return false;
		}
		
		WeaponObject weapon = (WeaponObject) core.objectService.getObject(actor.getWeaponId());
		
		if (weapon != null && weapon.getWeaponType() == command.getInvalidWeapon()) {
			return false;
		}
		
		for (long state : command.getInvalidStates()) {
			if ((actor.getStateBitmask() & state) == state) {
				//return false;
			}
		}
		
		// This SHOULD be invalid locomotions but we don't track these currently.
		// Postures are the best we can do.
		for (byte posture : command.getInvalidPostures()) {
			if (actor.getPosture() == posture) {
				//return false;
			}
		}
		
		switch (command.getTargetType()) {
			case 0: // Target Not Used For This Command
				break;
			case 1: // Other Only
				if (target == null || target == actor) {
					return false;
				}
				
				if (target.getContainer() == actor || target.getGrandparent() == actor) {
					break;
				}
				
				if (command.getMaxRangeToTarget() != 0 && actor.getPosition().getDistance(target.getPosition()) > command.getMaxRangeToTarget()) {
					return false;
				}
				
				if (!core.simulationService.checkLineOfSight(actor, target)) {
					return false;
				}
				
				break;
			case 2: // Self Only
				if (target == null) {
					target = actor;
				}
				
				if (target != actor && target instanceof CreatureObject) {
					return false;
				}
				
				break;
			case 3: // Free Target Mode (rally points, group waypoints)
				target = null;
				
				break;
			case 4: // Anyone
				if (target == null) {
					target = actor;
				}
				
				if (!core.simulationService.checkLineOfSight(actor, target)) {
					return false;
				}
				
				break;
			default:
				break;
		}
		
		switch (command.getTarget()) {
			case 0: // Ally Only
				if (target == null) {
					target = actor;
				}
				
				if (!(target instanceof TangibleObject)) {
					return false;
				}
				
				TangibleObject object = (TangibleObject) target;
				
				if (object.isAttackableBy(actor) || actor.getFactionStatus() < ((CreatureObject) object).getFactionStatus() || (!object.getFaction().equals("") && !object.getFaction().equals(actor.getFaction()))) {
					return false;
				}
				
				// Without this we could be buffing ally NPCs and such
				if (actor.getSlottedObject("ghost") != null && object.getSlottedObject("ghost") == null) {
					return false;
				}
				
				break;
			case 1: // Enemy Only
				if (target == null || !(target instanceof TangibleObject)) {
					return false;
				}
				
				TangibleObject targetObject = (TangibleObject) target;
				
				if (!targetObject.isAttackableBy(actor)) {
					return false;
				}
				
				break;
			case 2: // Indifferent
				break;
			default:
				break;
		}
		
		if (command.shouldCallOnTarget()) {
			if (target == null || !(target instanceof CreatureObject)) {
				return false;
			}
			
			actor = (CreatureObject) target;
		}
		
		long warmupTime = (long) (command.getWarmupTime() * 1000F);
		
		try {
			Thread.sleep(warmupTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		processCommand(actor, target, command, actionCounter, commandArgs);
		
		return true;
	}
	
	public void callCommand(SWGObject actor, String commandName, SWGObject target, String commandArgs) {
		callCommand((CreatureObject) actor, target, getCommandByName(commandName), 0, commandArgs);	
	}
	
	public BaseSWGCommand getCommandByCRC(int commandCRC) {
		Vector<BaseSWGCommand> commands = new Vector<BaseSWGCommand>(commandLookup);
		
		for (BaseSWGCommand command : commands) {
			if (command.getCommandCRC() == commandCRC) {
				return command;
			}
		}
		
		try {
			String[] tableArray = new String[] {
			"client_command_table", "command_table", "client_command_table_ground", "command_table_ground",
			"client_command_table_space", "command_table_space" };
			
			for (int n = 0; n < tableArray.length; n++) {
				DatatableVisitor visitor = ClientFileManager.loadFile("datatables/command/" + tableArray[n] + ".iff", DatatableVisitor.class);
				
				for (int i = 0; i < visitor.getRowCount(); i++) {
					if (visitor.getObject(i, 0) != null) {
						String name = ((String) visitor.getObject(i, 0)).toLowerCase();
						
						if (CRC.StringtoCRC(name) == commandCRC) {
							int sub = 0;
							
							boolean hasCharacterAbility = (((String) visitor.getObject(i, 7-sub)).length() > 0);
							
							if (tableArray[n].startsWith("client_") || tableArray[n].startsWith("command_table_")) {
								sub += 7;
							}
							
							if (tableArray[n].equals("client_command_table_space")) {
								sub += 5;
							}
							
							if (hasCharacterAbility || isCombatCommand(name)) {
								CombatCommand command = new CombatCommand(name.toLowerCase());
								commandLookup.add(command);
								return command;
							} else {
								BaseSWGCommand command = new BaseSWGCommand(name.toLowerCase());
								commandLookup.add(command);
								return command;
							}
						}
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public BaseSWGCommand getCommandByName(String name) {
		Vector<BaseSWGCommand> commands = new Vector<BaseSWGCommand>(commandLookup);
		
		name = name.toLowerCase();
		
		for (BaseSWGCommand command : commands) {
			if (command.getCommandName().equalsIgnoreCase(name)) {
				return command;
			}
		}
		
		try {
			String[] tableArray = new String[] {
			"client_command_table", "command_table", "client_command_table_ground", "command_table_ground",
			"client_command_table_space", "command_table_space" };
			
			for (int n = 0; n < tableArray.length; n++) {
				DatatableVisitor visitor = ClientFileManager.loadFile("datatables/command/" + tableArray[n] + ".iff", DatatableVisitor.class);
				
				for (int i = 0; i < visitor.getRowCount(); i++) {
					if (visitor.getObject(i, 0) != null) {
						String commandName = ((String) visitor.getObject(i, 0)).toLowerCase();
						
						if (commandName.equalsIgnoreCase(name)) {
							int sub = 0;
							
							boolean hasCharacterAbility = (((String) visitor.getObject(i, 7-sub)).length() > 0);
														
							if (tableArray[n].startsWith("client_") || tableArray[n].startsWith("command_table_")) {
								sub += 7;
							}
							
							if (tableArray[n].equals("client_command_table_space")) {
								sub += 5;
							}
							
							if (hasCharacterAbility || isCombatCommand(commandName)) {
								CombatCommand command = new CombatCommand(commandName);
								commandLookup.add(command);
								return command;
							} else {
								BaseSWGCommand command = new BaseSWGCommand(commandName);
								commandLookup.add(command);
								return command;
							}
						}
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean isCombatCommand(String commandName) {
		try {
			DatatableVisitor visitor = ClientFileManager.loadFile("datatables/combat/combat_data.iff", DatatableVisitor.class);
			
			for (int i = 0; i < visitor.getRowCount(); i++) {
				if (visitor.getObject(i, 0) != null && ((String) (visitor.getObject(i, 0))).equalsIgnoreCase(commandName)) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void processCommand(CreatureObject actor, SWGObject target, BaseSWGCommand command, int actionCounter, String commandArgs) {
		actor.addCooldown(command.getCooldownGroup(), command.getCooldown());
		
		if (command instanceof CombatCommand) {
			processCombatCommand(actor, target, (CombatCommand) command, actionCounter, commandArgs);
		} else {
			if (FileUtilities.doesFileExist("scripts/commands/" + command.getCommandName() + ".py")) {
				core.scriptService.callScript("scripts/commands/", command.getCommandName(), "run", core, actor, target, commandArgs);
			} else if (FileUtilities.doesFileExist("scripts/commands/combat/" + command.getCommandName() + ".py")) {
				core.scriptService.callScript("scripts/commands/combat/", command.getCommandName(), "run", core, actor, target, commandArgs);
			}
		}
	}
	
	public void processCombatCommand(CreatureObject attacker, SWGObject target, CombatCommand command, int actionCounter, String commandArgs) {
		if(FileUtilities.doesFileExist("scripts/commands/combat/" + command.getCommandName() + ".py"))
		{
			core.scriptService.callScript("scripts/commands/combat/", command.getCommandName(), "setup", core, attacker, target, command);
		}
		
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
			
			if(command.getHitType() == 0 && command.getBuffNameSelf() != null && command.getBuffNameSelf().length() > 0) {
				core.combatService.doSelfBuff(attacker, weapon, command, actionCounter);
				return;
			}
			for(int i = 0 ; i < command.getAttack_rolls(); i++) {
				core.combatService.doCombat(attacker, (TangibleObject) target, weapon, command, actionCounter);
			}
			
		}
		
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
		objControllerOpcodes.put(ObjControllerOpcodes.COMMAND_QUEUE_ENQUEUE, new INetworkRemoteEvent() {
			
			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				data.order(ByteOrder.LITTLE_ENDIAN);
				Client client = core.getClient(session);
				
				if (client == null) {
					System.out.println("NULL Client");
					return;
				}
				
				CommandEnqueue commandEnqueue = new CommandEnqueue();
				commandEnqueue.deserialize(data);
				
				int commandCRC = commandEnqueue.getCommandCRC();
				
				if (aliases.containsKey(commandEnqueue.getCommandCRC())) {
					commandCRC = aliases.get(commandCRC);
				}
				
				BaseSWGCommand command = getCommandByCRC(commandCRC);
				
				if (command == null) {
					//System.out.println("Unknown Command CRC: " + commandEnqueue.getCommandCRC());
					return;
				}
				
				if (client.getParent() == null) {
					System.out.println("NULL Object");
					return;
				}
				
				CreatureObject actor = (CreatureObject) client.getParent();
				
				SWGObject target = core.objectService.getObject(commandEnqueue.getTargetID());
				
				if (!callCommand(actor, target, command, commandEnqueue.getActionCounter(), commandEnqueue.getCommandArguments())) {
					// Call failScriptHook
				}
			}

		});
		
		objControllerOpcodes.put(ObjControllerOpcodes.COMMAND_QUEUE_REMOVE, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
			}
			
		});
		
		objControllerOpcodes.put(ObjControllerOpcodes.RESOURCE_EMPTY_HOPPER, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				data.order(ByteOrder.LITTLE_ENDIAN);
				Client client = core.getClient(session);			
				CommandEnqueue commandEnqueue = new CommandEnqueue();
												
//				StringBuilder sb = new StringBuilder();
//			    for (byte b : data.array()) {
//			        sb.append(String.format("%02X ", b));
//			    }
//			    System.out.println(sb.toString());
			    
			    /*
			    05 00 46 5E CE 80 83 00   00 00 ED 00 00 00 3E 45 
			    04 00 00 00 00 00 00 00   00 00 3E 45 04 00 00 00 
			    00 00 90 52 05 00 00 00   00 00 D7 35 05 00 00 00 
			    00 00 01 00 00 00 00 07			    
			    */
			    
			    long playerId = data.getLong(); // 3E 45 04 00 00 00 00 00
			    data.getInt();   // 00 00 00 00
			    data.getLong(); // 3E 45 04 00 00 00 00 00
			    long harvesterId = data.getLong(); // 1E 55 05 00 00 00 00 00
			    //long containerId = data.getLong(); // 1E 55 05 00 00 00 00 00  	Resources ID 
			    long resourceId = data.getLong(); // 1E 55 05 00 00 00 00 00  	Resources ID
			    int stackCount = data.getInt();   // Stack count
			    byte actionMode = data.get();     // 0 for retrieving, 1 for discarding 
			    byte updateCount = data.get();     // updateCount
	
				CreatureObject actor = (CreatureObject) client.getParent();
				SWGObject target = core.objectService.getObject(harvesterId);

				core.harvesterService.handleEmptyHopper(actor,target,harvesterId,resourceId,stackCount,actionMode,updateCount);
			}
		});
			
			
		
	}
	
	public void shutdown() {
		
	}
	
	public BaseSWGCommand registerCombatCommand(String name) { return getCommandByName(name); }
	public BaseSWGCommand registerCommand(String name) { return getCommandByName(name); }
	public BaseSWGCommand registerGmCommand(String name) { return getCommandByName(name); }
	
}
