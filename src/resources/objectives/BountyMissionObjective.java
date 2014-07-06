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

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import protocol.swg.CommPlayerMessage;
import protocol.swg.PlayClientEffectLocMessage;
import engine.resources.common.CRC;
import engine.resources.container.Traverser;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import main.NGECore;
import resources.common.BountyListItem;
import resources.common.OutOfBand;
import resources.common.SpawnPoint;
import resources.datatables.DisplayType;
import resources.objects.creature.CreatureObject;
import resources.objects.mission.MissionObject;
import resources.objects.waypoint.WaypointObject;
import services.chat.Mail;
import services.mission.MissionObjective;

public class BountyMissionObjective extends MissionObjective {

	private static final long serialVersionUID = 1L;

	private Point3D lastKnownLocation;
	private String lastKnownPlanet;
	private ScheduledFuture<?> seekerUpdates;
	private ScheduledFuture<?> probeSummonTask;
	private long markObjId;
	
	private boolean seekerActive = false;
	private String seekerPlanet = "";
	private boolean arakydActive = false;
	
	private Point3D arakydLandingLocation;
	
	////// Droids in the world - Set Posture to 8 to launch them. //////
	// Seeker Droids: object/creature/npc/droid/crafted/shared_probe_droid_advanced.iff
	// Arakyd Droids: object/creature/npc/droid/shared_imperial_probot_bounty.iff
	
	///// Inventory form /////
	// Seeker Droids: object/tangible/mission/shared_mission_bounty_droid_seeker.iff
	// Arakyd Droids: object/tangible/mission/shared_mission_bounty_droid_probot.iff
	public BountyMissionObjective(MissionObject parent) {
		super(parent);
	}
	
	@Override
	public void activate(NGECore core, CreatureObject player) {
		if (isActivated())
			return;
		
		BountyListItem bountyTarget = core.missionService.getBountyListItem(getMissionObject().getBountyMarkId());
		
		if (bountyTarget == null) {
			core.missionService.handleMissionAbort(player, getMissionObject());
			player.sendSystemMessage("@bountyhunter:null_mission", DisplayType.Broadcast);
			return;
		}
		
		bountyTarget.addBountyHunter(player.getObjectID());
		
		setMarkObjId(bountyTarget.getObjectID());
		
		String message = "@mission/mission_bounty_informant:target_hard_" + Integer.toString(new Random().nextInt(4) + 1);
		
		CommPlayerMessage comm = new CommPlayerMessage(player.getObjectId(), OutOfBand.ProsePackage(message));
		comm.setTime(2000);
		switch (bountyTarget.getFaction()) {
			case "neutral":
				comm.setModel("object/mobile/shared_dressed_assassin_mission_giver_0" + Integer.toString(new Random().nextInt(2) + 1) + ".iff");
				break;
			case "rebel":
				comm.setModel("object/mobile/shared_dressed_assassin_mission_giver_imp_hum_m_0" + Integer.toString(new Random().nextInt(2) + 1) + ".iff");
				break;
			case "imperial":
				comm.setModel("object/mobile/shared_dressed_assassin_mission_giver_reb_0" + Integer.toString(new Random().nextInt(2) + 1) + ".iff");
				break;
		} //Could also be: object/mobile/shared_bounty_guild_bounty_check.iff || object/mobile/shared_bounty_check_fugitive_x.iff
		
		player.getClient().getSession().write(comm.serialize());
		
		player.getPlayerObject().setBountyMissionId(getMissionObject().getObjectId());
		
		player.updatePvpStatus();
	}


	@Override
	public void complete(NGECore core, CreatureObject player) {
		BountyListItem bounty = core.missionService.getBountyListItem(markObjId);
		
		if (bounty == null)
			return;
		
		int reward = getMissionObject().getCreditReward();

		if (bounty.getCreditReward() - reward == 0)
			player.addBankCredits(reward);
		else
			player.addBankCredits(bounty.getCreditReward());
		
		bounty.removeBountyHunter(player.getObjectId());
		
		notifyBountyPlacers(core, player, bounty);
		
		clearActiveMissions(core, bounty);
		
		core.missionService.removeBounty(markObjId);

		player.getPlayerObject().setBountyMissionId(0);
		player.updatePvpStatus();
		
		cancelLocationUpdates();
		core.missionService.handleMissionAbort(player, getMissionObject(), true); // Do not call abort method
	}

	@Override
	public void abort(NGECore core, CreatureObject player) {
		cancelLocationUpdates();
		
		BountyListItem bounty = core.missionService.getBountyListItem(markObjId);
		
		if (bounty == null)
			return;
		
		bounty.removeBountyHunter(player.getObjectId());
		player.getPlayerObject().setBountyMissionId(0);
		player.updatePvpStatus();
	}

	@Override
	public void update(NGECore core, CreatureObject player) {
		
	}
	
	public void notifyBountyPlacers(NGECore core, CreatureObject player, BountyListItem bounty) {

        String message = "Dear Sir or Madam,\nThe Galactic Bounty Network wishes to inform you that the bounty that you placed on the head of "+ bounty.getName() 
        		+ " has been claimed by the bounty hunter, " + player.getCustomName() + ".\nThank you for your patronage, and have a nice day.";
        
		bounty.getBountyPlacers().forEach(id -> {
			Mail bountyMail = new Mail();
			bountyMail.setMailId(core.chatService.generateMailId());
			bountyMail.setTimeStamp((int) (new java.util.Date().getTime() / 1000));
			bountyMail.setRecieverId(id);
			bountyMail.setSubject("Bounty Claim Notification");
			bountyMail.setSenderName("Galactic Bounty Network");
			bountyMail.setMessage(message);
			bountyMail.setStatus(Mail.NEW);
			
			core.chatService.storePersistentMessage(bountyMail);
			
			SWGObject obj = core.objectService.getObject(id);
			if (obj != null)
				core.chatService.sendPersistentMessageHeader(obj.getClient(), bountyMail);
		});
	}
	
	public void clearActiveMissions(NGECore core, BountyListItem bounty) {
		bounty.getAssignedHunters().forEach(id -> {
			
			CreatureObject hunter = (CreatureObject) core.objectService.getObject(id);
			
			if (hunter != null) {
				hunter.sendSystemMessage(OutOfBand.ProsePackage("@bounty_hunter:bounty_failed_hunter", "TT", getMissionObject().getGrandparent().getObjectId()), DisplayType.Broadcast);
				hunter.getSlottedObject("datapad").viewChildren(hunter, true, false, new Traverser() {
					@Override
					public void process(SWGObject item) {
						if (item instanceof MissionObject) {
							MissionObject mission = (MissionObject) item;
							if (mission.getMissionType().equals("bounty")) {
								core.missionService.handleMissionAbort(hunter, mission);
							}
						}
					}
				});
			}
		});
	}
	
	public void checkBountyActiveStatus(NGECore core) {
		BountyListItem bountyTarget = core.missionService.getBountyListItem(getMissionObject().getBountyMarkId());
		CreatureObject player = (CreatureObject) getMissionObject().getGrandparent();

		if (bountyTarget == null || !bountyTarget.getAssignedHunters().contains(player.getObjectId())) {
			player.sendSystemMessage("@bounty_hunter:bounty_incomplete", DisplayType.Broadcast);
			core.missionService.handleMissionAbort(player, getMissionObject(), true);
		}
	}
	
	public void handleProbeDroidSummon(NGECore core, final CreatureObject actor) {
		
		actor.sendSystemMessage("@mission/mission_generic:probe_droid_launch_prep", DisplayType.Broadcast);
		AtomicInteger countDown = new AtomicInteger();
		
		ScheduledFuture<?> summon = Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				try {
					switch(countDown.incrementAndGet()) {
						case 15:
							actor.sendSystemMessage("@mission/mission_generic:probe_droid_arrival_5", DisplayType.Broadcast);
							break;
						case 16:
							actor.sendSystemMessage("@mission/mission_generic:probe_droid_arrival_4", DisplayType.Broadcast);
							break;
						case 17:
							actor.sendSystemMessage("@mission/mission_generic:probe_droid_arrival_3", DisplayType.Broadcast);
							break;
						case 18:
							actor.sendSystemMessage("@mission/mission_generic:probe_droid_arrival_2", DisplayType.Broadcast);
							break;
						case 19:
							actor.sendSystemMessage("@mission/mission_generic:probe_droid_arrival_1", DisplayType.Broadcast);
							arakydLandingLocation = SpawnPoint.getRandomPosition(actor.getPosition(), 50, 120, actor.getPlanetId());
							PlayClientEffectLocMessage effectMsg = new PlayClientEffectLocMessage("clienteffect/probot_delivery.cef", actor.getPlanet().getName(), arakydLandingLocation);
							actor.getClient().getSession().write(effectMsg.serialize());
							break;
						case 20:
							break;
						case 23:
							Quaternion ori = actor.getOrientation();
							
							CreatureObject probe = (CreatureObject) NGECore.getInstance().staticService.spawnObject("object/creature/npc/droid/shared_imperial_probot_bounty.iff", 
									actor.getPlanet().getName(), 0, arakydLandingLocation.x, arakydLandingLocation.y, arakydLandingLocation.z, ori.y, ori.w);
							
							// TODO: Move probe to the player.
							
							probe.setAttachment("probotRequester", actor.getObjectID());
							probe.setAttachment("attachedMission", getMissionObject().getObjectID());
							actor.sendSystemMessage("@mission/mission_generic:probe_droid_arrival", DisplayType.Broadcast);
							probeSummonTask.cancel(false);
							break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1, 1, TimeUnit.SECONDS);
		
		probeSummonTask = summon;
		
	}
	
	public void beginSeekerUpdates(NGECore core, CreatureObject actor) {
		
		if (!seekerActive) {
			setSeekerActive(true);
			setSeekerPlanet(actor.getPlanet().getName());
			
			AtomicInteger updates = new AtomicInteger();
			ScheduledFuture<?> updater = Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {

						@Override
						public void run() {
							try {
								MissionObject mission = getMissionObject();
								SWGObject target = core.objectService.getObject(markObjId);
								
								if (target == null) {
									actor.sendSystemMessage("@mission/mission_generic:player_target_inactive", DisplayType.Broadcast);
									cancelLocationUpdates();
									return;
								} else if (target.getPlanet().getName() != getSeekerPlanet()) {
									actor.sendSystemMessage("@mission/mission_generic:target_not_on_planet", DisplayType.Broadcast);
									cancelLocationUpdates();
									return;
								} else {
									
									if (updates.get() >= 6) {
										actor.sendSystemMessage("@mission/mission_generic:target_track_lost", DisplayType.Broadcast);
										cancelLocationUpdates();
										return;
									}
									
									WaypointObject missionWp = mission.getWaypoint();
	
									if (missionWp == null || missionWp.getPlanetCRC() != CRC.StringtoCRC(target.getPlanet().name)) {
										WaypointObject waypoint = (WaypointObject) getMissionObject().getWaypoint();
										waypoint.setActive(true);
										waypoint.setColor(WaypointObject.ORANGE);
										waypoint.setName(getMissionObject().getTargetName());
										waypoint.setPlanetCRC(CRC.StringtoCRC(target.getPlanet().getName()));
										waypoint.setStringAttribute("", "");
										waypoint.setPosition(target.getWorldPosition());
										mission.setWaypoint(waypoint);
									} else {
										missionWp.setPosition(target.getWorldPosition());
										mission.setWaypoint(missionWp);
									}
									actor.sendSystemMessage("@mission/mission_generic:target_location_updated_ground", DisplayType.Broadcast);
									actor.sendSystemMessage("@mission/mission_generic:target_continue_tracking", DisplayType.Broadcast);
									updates.getAndIncrement();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}, 60, 15, TimeUnit.SECONDS);
			
			seekerUpdates = updater;
		}
	}
	
	public void beginIdentifyTarget(NGECore core, CreatureObject actor) {
		if (seekerActive)
			return;
		
		setSeekerActive(true);
		setSeekerPlanet(actor.getPlanet().name);
		
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			MissionObject mission = getMissionObject();
			SWGObject target = core.objectService.getObject(markObjId);
			
			if (target == null) {
				actor.sendSystemMessage("@mission/mission_generic:player_target_inactive", DisplayType.Broadcast);
				return;
			} else if (target.getPlanet().getName() != getSeekerPlanet()) {
				actor.sendSystemMessage("@mission/mission_generic:target_not_on_planet", DisplayType.Broadcast);
				return;
			}
			
			WaypointObject missionWp = mission.getWaypoint();
			
			if (missionWp == null || missionWp.getPlanetCRC() != CRC.StringtoCRC(target.getPlanet().name)) {
				WaypointObject waypoint = (WaypointObject) getMissionObject().getWaypoint();
				waypoint.setActive(true);
				waypoint.setColor(WaypointObject.ORANGE);
				waypoint.setName(getMissionObject().getTargetName());
				waypoint.setPlanetCRC(CRC.StringtoCRC(target.getPlanet().getName()));
				waypoint.setStringAttribute("", "");
				waypoint.setPosition(target.getWorldPosition());
				mission.setWaypoint(waypoint);
			} else {
				missionWp.setPosition(target.getWorldPosition());
				mission.setWaypoint(missionWp);
			}
			
			actor.sendSystemMessage("Your target was identified as " + target.getCustomName() + ".", DisplayType.Broadcast); // Can't find this in the string files
			actor.sendSystemMessage("@mission/mission_generic:target_location_updated_ground", DisplayType.Broadcast);
		}, 60, TimeUnit.SECONDS);
	}
	
	public void beginArakydUpdate(final CreatureObject actor) {
		arakydActive = true;
		Executors.newScheduledThreadPool(1).schedule(() -> {
			try {
				int number = new Random().nextInt(100);
				
				// Effectively a 16% chance of failing. The failure type is calculated by subtracting 10 from the number received.
				if (number <= 16 && number != 0) { actor.sendSystemMessage("@mission/mission_generic:target_not_found_" + String.valueOf(10 - number), (byte) 0); return; }
				
				SWGObject target = NGECore.getInstance().objectService.getObject(markObjId);
				if (target == null) { actor.sendSystemMessage("@mission/mission_generic:player_target_inactive", DisplayType.Broadcast); }
				
				WaypointObject waypoint = (WaypointObject) NGECore.getInstance().objectService.createObject("object/waypoint/shared_waypoint.iff", target.getPlanet(), 
						target.getWorldPosition().x, target.getWorldPosition().z, target.getWorldPosition().y);
				waypoint.setActive(true);
				waypoint.setColor(WaypointObject.ORANGE);
				waypoint.setName(getMissionObject().getTargetName());
				waypoint.setPlanetCRC(CRC.StringtoCRC(target.getPlanet().getName()));
				waypoint.setStringAttribute("", "");
				getMissionObject().setWaypoint(waypoint);
				
				actor.sendSystemMessage("@mission/mission_generic:target_location_updated_ground", DisplayType.Broadcast);
				actor.sendSystemMessage("@mission/mission_generic:target_located_" + target.getPlanet().getName(), DisplayType.Broadcast);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 180, TimeUnit.SECONDS);
	}
	
	public void cancelLocationUpdates() {
		if (seekerUpdates != null)
			seekerUpdates.cancel(true);
		if (probeSummonTask != null)
			probeSummonTask.cancel(true);
		
		setSeekerActive(false);
		setSeekerPlanet("");
		setArakydActive(false);
	}
	
	public Point3D getLastKnownLocation() { return lastKnownLocation; }

	public void setLastKnownLocation(Point3D lastKnownLocation) { this.lastKnownLocation = lastKnownLocation; }

	public long getMarkObjId() { return markObjId; }

	public void setMarkObjId(long markObjId) { this.markObjId = markObjId; }

	public String getLastKnownPlanet() { return lastKnownPlanet; }

	public void setLastKnownPlanet(String lastKnownPlanet) { this.lastKnownPlanet = lastKnownPlanet; }

	public boolean isSeekerActive() { return seekerActive; }

	public void setSeekerActive(boolean seekerActive) { this.seekerActive = seekerActive; }

	public boolean isArakydActive() { return arakydActive; }

	public void setArakydActive(boolean araykdActive) { this.arakydActive = araykdActive; }
	
	public String getSeekerPlanet() { return seekerPlanet; }

	public void setSeekerPlanet(String seekerPlanet) { this.seekerPlanet = seekerPlanet; }

}
