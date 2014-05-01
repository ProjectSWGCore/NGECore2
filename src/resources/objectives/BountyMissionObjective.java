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

import protocol.swg.CommPlayerMessage;
import engine.resources.container.Traverser;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import main.NGECore;
import resources.common.BountyListItem;
import resources.common.OutOfBand;
import resources.common.ProsePackage;
import resources.datatables.DisplayType;
import resources.objects.creature.CreatureObject;
import resources.objects.mission.MissionObject;
import resources.objects.waypoint.WaypointObject;
import services.chat.Mail;
import services.mission.MissionObjective;

public class BountyMissionObjective extends MissionObjective {

	private Point3D lastKnownLocation;
	private String lastKnownPlanet;
	private ScheduledFuture<?> locationUpdater;
	private long markObjId;
	
	private boolean seekerActive = false;
	private String seekerPlanet = "";
	private boolean arakydActive = false;

	public BountyMissionObjective(MissionObject parent) {
		super(parent);
	}
	
	@Override
	public void activate(NGECore core, CreatureObject player) {
		if (isActivated())
			return;
		
		BountyListItem bountyTarget = core.missionService.getBountyListItem(getMissionObject().getBountyObjId());
		
		if (bountyTarget == null) {
			core.missionService.handleMissionAbort(player, getMissionObject());
			player.sendSystemMessage("@bountyhunter:null_mission", DisplayType.Broadcast);
			return;
		}
		
		bountyTarget.addBountyHunter(player.getObjectId());
		core.missionService.updateBounty(bountyTarget);
		
		setMarkObjId(bountyTarget.getObjectId());
		
		String message = "@mission/mission_bounty_informant:target_hard_" + Integer.toString(new Random().nextInt(4) + 1);
		
		CommPlayerMessage comm = new CommPlayerMessage(player.getObjectId(), new OutOfBand(new ProsePackage(message)));
		comm.setTime(5000);
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
		
		notifyBountyPlacers(core, player, bounty);
		
		clearActiveMissions(core, bounty);
		
		core.missionService.removeBounty(markObjId);
	}

	@Override
	public void abort(NGECore core, CreatureObject player) {
		BountyListItem bounty = core.missionService.getBountyListItem(markObjId);
		
		if (bounty == null)
			return;
		
		bounty.removeBountyHunter(player.getObjectId());
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
				hunter.sendSystemMessage(new OutOfBand(new ProsePackage("@bounty_hunter:bounty_failed_hunter", "TT", getMissionObject().getGrandparent().getObjectId())), DisplayType.Broadcast);
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
		BountyListItem bountyTarget = core.missionService.getBountyListItem(getMissionObject().getBountyObjId());
		CreatureObject player = (CreatureObject) getMissionObject().getGrandparent();

		if (bountyTarget == null || !bountyTarget.getAssignedHunters().contains(player.getObjectId())) {
			player.sendSystemMessage("@bounty_hunter:bounty_incomplete", DisplayType.Broadcast);
			core.missionService.handleMissionAbort(player, getMissionObject(), true);
		}
	}
	
	public void beginSeekerUpdates(NGECore core) {
		
		if (!seekerActive) {
			setSeekerActive(true);
			setSeekerPlanet(getMissionObject().getGrandparent().getPlanet().getName());
			
			ScheduledFuture<?> updates = Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {

						@Override
						public void run() {
							
							MissionObject mission = getMissionObject();
							SWGObject target = core.objectService.getObject(markObjId);
							CreatureObject player = (CreatureObject) mission.getGrandparent();

							if (target == null) {
								player.sendSystemMessage("@mission/mission_generic:player_target_inactive", DisplayType.Broadcast);
								cancelLocationUpdates();
								return;
							} else if (target.getPlanet().getName() != getSeekerPlanet()) {
								player.sendSystemMessage("@mission/mission_generic:target_not_on_planet", DisplayType.Broadcast);
								cancelLocationUpdates();
								return;
							} else {
								WaypointObject missionWp = mission.getAttachedWaypoint();

								if (missionWp == null) {
									player.sendSystemMessage("@mission/mission_generic:target_not_found_1", DisplayType.Broadcast);
									cancelLocationUpdates();
									return;
								}
								missionWp.setPosition(target.getWorldPosition());

								mission.setAttachedWaypoint(missionWp);
								player.sendSystemMessage("@mission/mission_generic:target_location_updated_ground", DisplayType.Broadcast);
							}
						}
					}, 60, 15, TimeUnit.SECONDS);
			
			setLocationUpdater(updates);
		}
	}
	
	public void cancelLocationUpdates() {
		getLocationUpdater().cancel(true);
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

	public ScheduledFuture<?> getLocationUpdater() { return locationUpdater; }

	public void setLocationUpdater(ScheduledFuture<?> locationUpdater) { this.locationUpdater = locationUpdater; }

	public boolean isSeekerActive() { return seekerActive; }

	public void setSeekerActive(boolean seekerActive) { this.seekerActive = seekerActive; }

	public boolean isArakydActive() { return arakydActive; }

	public void setArakydActive(boolean araykdActive) { this.arakydActive = araykdActive; }
	
	public String getSeekerPlanet() { return seekerPlanet; }

	public void setSeekerPlanet(String seekerPlanet) { this.seekerPlanet = seekerPlanet; }

}
