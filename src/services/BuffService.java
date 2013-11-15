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
package services;

import java.nio.ByteOrder;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;











import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.ObjControllerMessage;
import protocol.swg.objectControllerObjects.BuffBuilderChange;
import protocol.swg.objectControllerObjects.BuffBuilderStartMessage;
import resources.common.Console;
import resources.common.FileUtilities;
import resources.common.ObjControllerOpcodes;
import resources.objects.Buff;
import resources.objects.DamageOverTime;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import main.NGECore;
import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class BuffService implements INetworkDispatch {
	
	private NGECore core;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public BuffService(NGECore core) {
		this.core = core;
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		// BUFF_BUILDER_CHANGE sent every time something is clicked on in the Buff Builder. It's also sent when the buff session is started.
		objControllerOpcodes.put(ObjControllerOpcodes.BUFF_BUILDER_CHANGE, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				Console.println("BUFF_BUILDER_CHANGE RECIEVED");
				data.order(ByteOrder.LITTLE_ENDIAN);
				
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));
				
				if(client == null)
					return;
				
				SWGObject sender = client.getParent();
				
				if(sender == null)
					return;
				
				BuffBuilderChange changeMessage = new BuffBuilderChange();
				changeMessage.deserialize(data);
				
				SWGObject recipient = core.objectService.getObject(changeMessage.getRecipientId());
				
				if (recipient == null || recipient.getClient() == null)
					return;
				
				if (sender.getClient().getSession().containsAttribute("buffWorkshop")) {

					long attribute = (long) sender.getClient().getSession().getAttribute("buffWorkshop");
					Console.println("Sender Attribute = " + attribute);
					Console.println("Recipient ID (Packet) = " + changeMessage.getRecipientId());
					
					if (attribute == changeMessage.getObjectId()) {
						// TODO: accepted checked? give buff
						Console.println("Buffer buffing themselves!");
						return;
					}
					
					else if (attribute == changeMessage.getRecipientId() && attribute != changeMessage.getObjectId()) {
						Console.println("Attribute check passed!"); // LAST TRIED BYTE 6
						BuffBuilderChange recievingMsg = new BuffBuilderChange(recipient.getObjectId(), changeMessage.getBufferId(), changeMessage.getRecipientId(), 0, changeMessage.getBuffCost(), (byte) 7);
						recievingMsg.setStartTime(changeMessage.getStartTime());
						recievingMsg.setTickCount(changeMessage.getTickCount() + 1);
						
						ObjControllerMessage objRcController = new ObjControllerMessage(0x23, recievingMsg);
						recipient.getClient().getSession().write(objRcController.serialize());
						Console.println("Msg sent to: " + recipient.getCustomName() + " that has an ID of: " + recipient.getObjectId());
						Console.println("ObjectId: " + recievingMsg.getObjectId());
						Console.println("BufferId: " + recievingMsg.getBufferId());
						Console.println("RecipientId: " + recievingMsg.getRecipientId());
						Console.println("Buff Cost: " + recievingMsg.getBuffCost());
						Console.println("MessageRecipientId: " + recievingMsg.getRecipientId());
						
						Console.println("BYTE: " + recievingMsg.getUnkByte());
						
					}

				} else {
					
					// Don't need a Buff Recipient Window for buffing themselves..
					if (changeMessage.getObjectId() == changeMessage.getRecipientId()) { 
						sender.getClient().getSession().setAttribute("buffWorkshop", recipient.getObjectId());
						Console.println("Sender attribute set to: " + sender.getClient().getSession().getAttribute("buffWorkshop").toString());
						return;
					}
					
					BuffBuilderStartMessage startMsg = new BuffBuilderStartMessage(recipient.getObjectId(), sender.getObjectId(), recipient.getObjectId());
					ObjControllerMessage objController = new ObjControllerMessage(11, startMsg);
					recipient.getClient().getSession().write(objController.serialize());
					
					recipient.getClient().getSession().setAttribute("buffWorkshop", changeMessage.getObjectId());
					Console.println("Recipient attribute set to: " + recipient.getClient().getSession().getAttribute("buffWorkshop").toString());
					sender.getClient().getSession().setAttribute("buffWorkshop", recipient.getObjectId());
					Console.println("Sender attribute set to: " + sender.getClient().getSession().getAttribute("buffWorkshop").toString());
				}
			}
		});
	}

	@Override
	public void shutdown() {
		
	}
	
	public void addBuffToCreature(final CreatureObject creature, String buffName) {		

		/*if(!FileUtilities.doesFileExist("scripts/buffs/" + buffName + ".py")) {
			//System.out.println("Buff script doesnt exist for: " + buffName);
			return;
		}*/
		
		final Buff buff = new Buff(buffName, creature.getObjectID());
		buff.setTotalPlayTime(((PlayerObject) creature.getSlottedObject("ghost")).getTotalPlayTime());
		
	
            for (final Buff otherBuff : creature.getBuffList()) {
                if (buff.getGroup1().equals(otherBuff.getGroup1()))  
                	if (buff.getPriority() >= otherBuff.getPriority()) {
                        if (buff.getBuffName().equals(otherBuff.getBuffName())) {
                        	
                        		if(otherBuff.getStacks() < otherBuff.getMaxStacks()) {
                        			
                        			buff.setStacks(otherBuff.getStacks() + 1);
                        			if(creature.getDotByBuff(otherBuff) != null)	// reset duration when theres a dot stack
                        				creature.getDotByBuff(otherBuff).setStartTime(buff.getStartTime());
                        			
                        		}
                        	
                                if (otherBuff.getRemainingDuration() > buff.getDuration() && otherBuff.getStacks() >= otherBuff.getMaxStacks()) {
                                        return;
                                }
                        }
                       
                        removeBuffFromCreature(creature, otherBuff);
                        break;
                } else {
                	System.out.println("buff not added:" + buffName);
                	return;
                }
        }	
			
        if(FileUtilities.doesFileExist("scripts/buffs/" + buffName + ".py"))
        	core.scriptService.callScript("scripts/buffs/", "setup", buffName, core, creature, buff);
		
		creature.addBuff(buff);
		
		if(buff.getDuration() > 0) {
			
			ScheduledFuture<?> task = scheduler.schedule(new Runnable() {
	
				@Override
				public void run() {
					
					removeBuffFromCreature(creature, buff);
					
				
				}
				
			}, (long) buff.getDuration(), TimeUnit.SECONDS);
			
			buff.setRemovalTask(task);
			
		}
		
	} 
	
	public void removeBuffFromCreature(CreatureObject creature, Buff buff) {
		
		 if(!creature.getBuffList().contains(buff))
             return;
		 DamageOverTime dot = creature.getDotByBuff(buff);
         if(dot != null) {
        	 dot.getTask().cancel(true);
        	 creature.removeDot(dot);
         }
         if(FileUtilities.doesFileExist("scripts/buffs/" + buff.getBuffName() + ".py"))
        	 core.scriptService.callScript("scripts/buffs/", "removeBuff", buff.getBuffName(), core, creature, buff);
         creature.removeBuff(buff);
         
		
	}
	
	public void clearBuffs(final CreatureObject creature) {
		
		// copy to array for thread safety
					
		for(final Buff buff : creature.getBuffList().get().toArray(new Buff[] { })) {
			
			if (buff.getGroup1().startsWith("setBonus")) { continue; }

			if(buff.getRemainingDuration() > 0 && buff.getDuration() > 0) {
				ScheduledFuture<?> task = scheduler.schedule(new Runnable() {

					@Override
					public void run() {
						
						removeBuffFromCreature(creature, buff);
						
					}
					
				}, (long) buff.getRemainingDuration(), TimeUnit.SECONDS);
				buff.setRemovalTask(task);
				continue;
			} else {
				removeBuffFromCreature(creature, buff);
			}
				
		}
					
	}
	
	

}
