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

import java.io.UnsupportedEncodingException;
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
import protocol.swg.objectControllerObjects.StartTask;
import resources.objects.creature.CreatureObject;
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
		
		if (command.getCharacterAbility().length() > 0 && !actor.hasAbility(command.getCharacterAbility()) && actor.getClient() != null) {
			return false;
		}
		
		if (command.isDisabled()) {
			return false;
		}
		
		if (actor.getClient() != null && command.getGodLevel() > actor.getPlayerObject().getGodLevel()) {
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
			case 1: // Other Only (objectId/targetName)
				if (target == null) {
					if (commandArgs != null && !commandArgs.equals("")) {
						String name = commandArgs.split(" ")[0];
						
						target = core.objectService.getObjectByFirstName(name);
						
						if (target == actor) {
							target = null;
						}
					}
					
					break;
				}
				
				if (target == actor) {
					return false;
				}
				
				if (target.getContainer() == actor || target.getGrandparent() == actor) {
					break;
				}
				
				if (command.getMaxRangeToTarget() != 0 && actor.getPosition().getDistance(target.getPosition()) > command.getMaxRangeToTarget()) {
					return false;
				}
				
				if (!core.simulationService.checkLineOfSight(actor, target)) {
					actor.showFlyText("@combat_effects:cant_see", 1.5f, new RGB(72, 209, 204), 1, true);
					return false;
				}
				
				break;
			case 2: // Anyone (objectId/targetName)
				if (target == null) {
					if (commandArgs != null && !commandArgs.equals("")) {
						String name = commandArgs.split(" ")[0];
						
						target = core.objectService.getObjectByFirstName(name);
						
						if (target == actor) {
							target = null;
						}
					}
					
					break;
				}
				
				if (target.getContainer() == actor || target.getGrandparent() == actor) {
					break;
				}
				
				if (command.getMaxRangeToTarget() != 0 && actor.getPosition().getDistance(target.getPosition()) > command.getMaxRangeToTarget()) {
					return false;
				}
				
				if (!core.simulationService.checkLineOfSight(actor, target)) {
					actor.showFlyText("@combat_effects:cant_see", 1.5f, new RGB(72, 209, 204), 1, true);
					return false;
				}
				
				break;
			case 3: // Free Target Mode (rally points, group waypoints)
				target = null;
				
				if (commandArgs == null) {
					break;
				}
				
				String[] args = commandArgs.split(" ");
				
				float x = 0, y = 0, z = 0;
				
				try {
					if (args.length == 2) {
						x = Integer.valueOf(args[0]);
						z = Integer.valueOf(args[1]);
					} else if (args.length > 2) {
						x = Integer.valueOf(args[0]);
						y = Integer.valueOf(args[1]);
						z = Integer.valueOf(args[2]);
					} else {
						return false;
					}
				} catch (NumberFormatException e) {
					return false;
				}
				
				Point3D position = new Point3D(x, y, z);
				
				if (command.getMaxRangeToTarget() != 0 && actor.getPosition().getDistance(position) > command.getMaxRangeToTarget()) {
					return false;
				}
				
				break;
			case 4: // Any object
				if (target == null) {
					break;
				}
				
				if (command.getMaxRangeToTarget() != 0 && actor.getPosition().getDistance(target.getPosition()) > command.getMaxRangeToTarget()) {
					return false;
				}
				
				if (!core.simulationService.checkLineOfSight(actor, target)) {
					actor.showFlyText("@combat_effects:cant_see", 1.5f, new RGB(72, 209, 204), 1, true);
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
				
				if (!object.getFaction().equals("") && !object.getFaction().equals(actor.getFaction())) {
					return false;
				}
				
				if (actor.getFactionStatus() < object.getFactionStatus()) {
					return false;
				}
				
				if (object.isAttackableBy(actor)) {
					return false;
				}
				
				// Without this we could be buffing ally NPCs and such
				// Think this is checked later on
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
		
		long warmupTime = (long) (command.getWarmupTime() * 1000f);
		
		if(warmupTime > 0) {
			try {
				Thread.sleep(warmupTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
							if (isCombatCommand(name)) {
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
							if (isCombatCommand(commandName)) {
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
		if (command.getCooldown() > 0f) {
			actor.addCooldown(command.getCooldownGroup(), command.getCooldown());
		}
		
		if (command instanceof CombatCommand) {
			processCombatCommand(actor, target, (CombatCommand) command, actionCounter, commandArgs);
		} else {
			if (FileUtilities.doesFileExist("scripts/commands/" + command.getCommandName().toLowerCase() + ".py")) {
				core.scriptService.callScript("scripts/commands/", command.getCommandName().toLowerCase(), "run", core, actor, target, commandArgs);
			} else if (FileUtilities.doesFileExist("scripts/commands/combat/" + command.getCommandName().toLowerCase() + ".py")) {
				core.scriptService.callScript("scripts/commands/combat/", command.getCommandName().toLowerCase(), "run", core, actor, target, commandArgs);
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
			attacker.showFlyText("@combat_effects:cant_see", 1.5f, new RGB(72, 209, 204), 1, true);
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
	
	public void removeCommand(CreatureObject actor, int actionCounter, BaseSWGCommand command) {
		if (actor == null || actor.getClient() == null || command == null) {
			return;
		}
		
		actor.getClient().getSession().write(new ObjControllerMessage(0x0B, new CommandEnqueueRemove(actor.getObjectId(), actionCounter)).serialize());
		actor.removeCooldown(actionCounter, command);
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
					removeCommand(actor, commandEnqueue.getActionCounter(), command);
				}
			}

		});
		
		objControllerOpcodes.put(ObjControllerOpcodes.COMMAND_QUEUE_REMOVE, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
			}
			
		});
		
		objControllerOpcodes.put(ObjControllerOpcodes.CRAFT_FILLSLOT, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				Client client = core.getClient(session);

				if(client == null) {
					System.out.println("NULL Client");
					return;
				}

				CommandEnqueue commandEnqueue = new CommandEnqueue();						
				CreatureObject actor = (CreatureObject) client.getParent();
				SWGObject target = core.objectService.getObject(commandEnqueue.getTargetID());
								
//				StringBuilder sb = new StringBuilder();
//			    for (byte b : data.array()) {
//			        sb.append(String.format("%02X ", b));
//			    }
//			    System.out.println(sb.toString());
			
//			    05 00 46 5E CE 80 83 00   00 00 07 01 00 00 72 14 
//			    09 00 00 00 00 00 00 00   00 00 04 2A 09 00 00 00 
//			    00 00 01 00 00 00 00 00   00 00 04 00 00 00 00 00 
//			    00 00 00 00 00 00 00 00   00 00 00 00 
			    
			    long playerId = data.getLong(); 
			    data.getInt();   // 00 00 00 00
			    long ingredientId = data.getLong(); 
			    int slotNumber = data.getInt(); 
			    int option = data.getInt();
			    byte sequence = data.get();
//core.craftingService.handleCraftFillSlot(playerId, ingredientId, slotNumber, option, sequence);
			}
		});	

		objControllerOpcodes.put(ObjControllerOpcodes.CRAFT_EMPTYSLOT, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				Client client = core.getClient(session);

				if(client == null) {
					System.out.println("NULL Client");
					return;
				}

				CommandEnqueue commandEnqueue = new CommandEnqueue();						
				CreatureObject actor = (CreatureObject) client.getParent();
				SWGObject target = core.objectService.getObject(commandEnqueue.getTargetID());
				
			    long playerId = data.getLong(); 
			    data.getInt();   // 00 00 00 00
			    int slotNumber = data.getInt(); 
			    long ingredientId = data.getLong(); 			    
			    byte sequence = data.get();		
//core.craftingService.handleCraftEmptySlot(playerId, ingredientId, slotNumber, sequence);
			}

		});	
		
		
		objControllerOpcodes.put(ObjControllerOpcodes.CRAFT_CUSTOMIZATION, new INetworkRemoteEvent() {
			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				data.order(ByteOrder.LITTLE_ENDIAN);
				Client client = core.getClient(session);
				if(client == null) {
					System.out.println("NULL Client");
					return;
				}

			    long playerId = data.getLong(); 
			    data.getInt();   // 00 00 00 00 UTF-16LE
			    
			    String enteredName = "";
			    int length = 2*data.order(ByteOrder.LITTLE_ENDIAN).getInt();				
				int bufferPosition = data.position();				
				try
				{				
					enteredName = new String(data.array(), bufferPosition, length, "UTF-16LE");					
				}
				catch (UnsupportedEncodingException e)
				{					
					System.err.println("UnsupportedEncodingException while reading crafting customization name ");
				}

			    System.err.println("enteredName " + enteredName);
			    data.position(bufferPosition + length);
			    
			    byte modelNumber = data.get();
				int schematicQuantity  = data.getInt(); 
				byte customizationList = data.get();
				for (int i=0;i<(int)customizationList;i++){
					int customizationSlot = data.getInt(); 
					int customizationValue = data.getInt(); 
				}

//core.craftingService.handleCraftCustomization(playerId,enteredName,modelNumber,schematicQuantity,customizationList);
			}

		});
		
//		05 00 46 5E CE 80 83 00   00 00 5A 01 00 00 75 59
//		0F 00 00 00 00 00 00 00   00 00 1D 00 00 00 63 00 
//		72 00 61 00 66 00 74 00   69 00 6E 00 67 00 3A 00 
//		5B 00 65 00 78 00 6F 00   5F 00 70 00 72 00 6F 00 
//		74 00 65 00 69 00 6E 00   5F 00 77 00 61 00 66 00 
//		65 00 72 00 73 00 5D 00   FF E8 03 00 00 00 00 00 
//		00 00 00 00 00 00 00 00   00 00 00 00 00 00 00 00 
//		00 00 00 00 00 00 00 00   00 00 00 00 
		
//		USTRING:	CustomName
//		BYTE:		ModelNumber
//		INT:		SchematicQuantity
//		BYTE:		CustomizationList
//		{
//		  INT:		CustomizationSlot
//		  INT:		CustomizationValue
//		}
	
		
	}
	
	public void shutdown() {
		
	}
	
	public BaseSWGCommand registerCommand(String name) {
		return getCommandByName(name);
	}
	
	public CombatCommand registerCombatCommand(String name) {
		BaseSWGCommand command = getCommandByName(name);
		
		if (command instanceof CombatCommand) {
			return (CombatCommand) command;
		}
		
		return null;
	}
	
	public BaseSWGCommand registerGmCommand(String name) {
		BaseSWGCommand command = getCommandByName(name);
		//if(command != null)
			//command.setGodLevel(5); // not sure if still needed after more recent commits
		return command;
	}
	
}
