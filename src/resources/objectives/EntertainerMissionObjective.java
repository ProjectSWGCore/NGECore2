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

import java.util.concurrent.atomic.AtomicInteger;

import main.NGECore;
import resources.common.OutOfBand;
import resources.datatables.DisplayType;
import resources.objects.creature.CreatureObject;
import resources.objects.mission.MissionObject;
import services.mission.MissionObjective;

public class EntertainerMissionObjective extends MissionObjective{
	
	private static final long serialVersionUID = 1L;

	private int entertainerMissionNum;

	public EntertainerMissionObjective(MissionObject parent) {
		super(parent);
	}

	@Override
	public void activate(NGECore core, CreatureObject player) {
		
		// This way will allow 2 Entertainer missions at a time.
		AtomicInteger number = new AtomicInteger();
		player.getSlottedObject("datapad").viewChildren(player, true, false, (obj) -> {
			if (obj instanceof MissionObject && ((MissionObject) obj).getMissionType().equals("entertainer")) { number.getAndIncrement(); }
		});
		player.setAttachment("entertainerMission" + Integer.valueOf(number.get()), getMissionObject().getObjectID());
		entertainerMissionNum = number.get();
		
	}

	@Override
	public void complete(NGECore core, CreatureObject player) {
		int reward = getMissionObject().getCreditReward();
		player.addBankCredits(getMissionObject().getCreditReward());
		player.setAttachment("entertainerMission" + Integer.valueOf(entertainerMissionNum), 0);
		player.sendSystemMessage(OutOfBand.ProsePackage("@mission/mission_generic:success_w_amount", reward), DisplayType.Broadcast);
	}

	@Override
	public void abort(NGECore core, CreatureObject player) {
		player.setAttachment("entertainerMission" + Integer.valueOf(entertainerMissionNum), 0);
	}

	@Override
	public void update(NGECore core, CreatureObject player) {
	}

}
