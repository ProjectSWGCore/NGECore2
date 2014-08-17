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
package resources.objectives;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import engine.resources.common.CRC;
import main.NGECore;
import resources.common.OutOfBand;
import resources.datatables.DisplayType;
import resources.objects.creature.CreatureObject;
import resources.objects.mission.MissionObject;
import resources.objects.waypoint.WaypointObject;
import services.mission.MissionObjective;

public class EntertainerMissionObjective extends MissionObjective implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public EntertainerMissionObjective(MissionObject parent) {
		super(parent);
	}

	@Override
	public void activate(NGECore core, CreatureObject player) {
		
		if (isActivated())
			return;
		
		WaypointObject waypoint = getMissionObject().getWaypoint();
		waypoint.setPlanetCrc(CRC.StringtoCRC(player.getPlanet().name));
		waypoint.setPosition(parent.getDestinationLocation().getLocation());
		waypoint.setName("@" + parent.getTitle().getStfFilename() + ":" + "m" + parent.getMissionId() + "t");
		waypoint.setColor(WaypointObject.BLUE);
		waypoint.setActive(true);
		getMissionObject().setWaypoint(waypoint);
		
		setActive(true);
		checkForEntertainermissionDistance(core, player);
	}

	@Override
	public void complete(NGECore core, CreatureObject player) {
		int reward = getMissionObject().getCreditReward();		
		player.addBankCredits(reward);		
		player.sendSystemMessage(OutOfBand.ProsePackage("@mission/mission_generic:success_w_amount", reward), DisplayType.Broadcast);
		if(player.getAttachment("Entertainermission") != null){
			((ScheduledFuture<?>)player.getAttachment("Entertainermission")).cancel(true);
			player.setAttachment("Entertainermission", null);
		}
		core.missionService.handleMissionAbort(player, getMissionObject(), true); 		
		
		if(player.getAttachment("timertask") != null || player.getAttachment("timer") != null){
			if(player.getAttachment("timertask") != null){
				((TimerTask) player.getAttachment("timertask")).cancel();
				player.setAttachment("timertask", null);
			}
			if(player.getAttachment("timer") != null){
				((Timer) player.getAttachment("timer")).cancel();
				player.setAttachment("timer", null);
			}			
		}
	}

	@Override
	public void abort(NGECore core, CreatureObject player) {
		
		if(player.getAttachment("Entertainermission") != null){
			((ScheduledFuture<?>)player.getAttachment("Entertainermission")).cancel(true);
			player.setAttachment("Entertainermission", null);
			if(getObjectivePhase() >= 2 )
				if(player.getAttachment("timertask") != null || player.getAttachment("timer") != null){
					if(player.getAttachment("timertask") != null){
						((TimerTask) player.getAttachment("timertask")).cancel();
						player.setAttachment("timertask", null);
					}
					if(player.getAttachment("timer") != null){
						((Timer) player.getAttachment("timer")).cancel();
						player.setAttachment("timer", null);
					}
				}
		}
	}

	@Override
	public void update(NGECore core, CreatureObject player) {
		
		setObjectivePhase(getObjectivePhase() + 1);
		
		if(getObjectivePhase() == 1){
			WaypointObject waypoint = (WaypointObject) core.objectService.createObject("object/waypoint/shared_waypoint.iff", parent.getPlanet());
			waypoint.setPosition(parent.getDestinationLocation().getLocation());
			waypoint.setName("@" + parent.getTitle().getStfFilename() + ":" + "m" + parent.getMissionId() + "t");
			waypoint.setColor(WaypointObject.PURPLE);
			waypoint.setActive(true);
			waypoint.setPlanetCrc(CRC.StringtoCRC(player.getPlanet().name));
			getMissionObject().setTargetName("Start Dancing");
			getMissionObject().setWaypoint(waypoint);
			update(core, player);
		}
		if(getObjectivePhase() >= 2){
			if(player.getAttachment("timertask") == null ){
				updateTimer(core, player);
			}
			
			if (player.getPosture() == 9 ){
				if(player.getAttachment("isentertaining") == null){
					getMissionObject().setTargetName("Dance for 10 Minutes");
					isPerforming(core, player);
					((TimerTask) player.getAttachment("timertask")).cancel();
					((Timer) player.getAttachment("timer")).cancel();
				}
			}		
		}
	}
	
	public void updateTimer(NGECore core, CreatureObject player){
		Timer t = new Timer();
		TimerTask tt = new TimerTask() {
		    @Override
		    public void run() {
		       update(core, player);
		    };
		};
		t.scheduleAtFixedRate(tt,0,10000);
		player.setAttachment("timertask", tt);
		player.setAttachment("timer", t);
	}
	
	public void checkForEntertainermissionDistance(NGECore core, CreatureObject player){
		ScheduledFuture<?> entertainerMissionTask = Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
			public void run() {
				if (player != null && parent != null && getMissionObject() != null) {
					if(player.getPosition() != null &&  parent.getDestinationLocation() != null &&  parent.getDestinationLocation().getLocation() != null) {
						if(player.getPosition().getDistance2D( parent.getDestinationLocation().getLocation()) <= 2){
							update(core, player);
							((ScheduledFuture<?>)player.getAttachment("Entertainermission")).cancel(true);
							if (player.getAttachment("Entertainermission") != null){
								player.setAttachment("Entertainermission", null);
							}
						}
					} else {
						((ScheduledFuture<?>)player.getAttachment("Entertainermission")).cancel(true);
						if (player.getAttachment("Entertainermission") != null){
							player.setAttachment("Entertainermission", null);
						}
					}
				} else {
					((ScheduledFuture<?>)player.getAttachment("Entertainermission")).cancel(true);
					if (player.getAttachment("Entertainermission") != null){
						player.setAttachment("Entertainermission", null);
					}
				}
			}
		}, 0, 1, TimeUnit.SECONDS);
		player.setAttachment("Entertainermission", entertainerMissionTask);
	}

	public void isPerforming(NGECore core, CreatureObject player){
		
		ScheduledFuture<?> isPerformingTask  = Executors.newScheduledThreadPool(1).schedule(new Runnable() {
			public void run() {
				try {

					if (player != null){
						player.sendSystemMessage("You start Dancing!", DisplayType.Broadcast);
						Thread.sleep(300000);
						player.sendSystemMessage("5 Minutes left!", DisplayType.Broadcast);
						Thread.sleep(240000);
						player.sendSystemMessage("1 Minute left!", DisplayType.Broadcast);
						Thread.sleep(50000);
						for(int seconds = 10; seconds > 1; seconds--) {
							player.sendSystemMessage(seconds + " seconds left!", DisplayType.Broadcast);
							Thread.sleep(1000);
						}
						complete(core, player);
						player.setAttachment("isentertaining", null);
					}
				}catch (Exception e) {
					e.printStackTrace();					
				}
			}
		}, 1, TimeUnit.SECONDS);
		player.setAttachment("isentertaining", isPerformingTask);
	}
}