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

import resources.objects.mission.MissionObject;
import resources.objects.tangible.TangibleObject;
import services.mission.MissionObjective;

public class DeliveryMissionObjective extends MissionObjective {

	private TangibleObject deliveryObject;
	private int objectivePhase;

	public DeliveryMissionObjective(MissionObject parent) {
		super(parent);
		this.objectivePhase = 0;
	}

	@Override
	public void activate() {
	}

	@Override
	public void complete() {
	}

	@Override
	public void drop() {
	}

	public TangibleObject getDeliveryObject() { return deliveryObject; }

	public int getObjectivePhase() { return objectivePhase; }
}
