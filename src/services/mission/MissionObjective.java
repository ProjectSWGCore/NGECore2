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
package services.mission;

import java.io.Serializable;

import main.NGECore;

import engine.resources.objects.Delta;
import engine.resources.objects.SWGObject;
import resources.objects.creature.CreatureObject;
import resources.objects.mission.MissionObject;

public abstract class MissionObjective extends Delta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected long startTime;
	protected boolean activated;
	protected transient MissionObject parent;
	private int objectivePhase;
	
	public MissionObjective() { }
	
	public MissionObjective(MissionObject parent) {
		this.startTime = System.currentTimeMillis();
		this.activated = false;
		this.parent = parent;
		this.objectivePhase = 0;
	}
	
	public void init(SWGObject object) {
		super.init(object);
		parent = (MissionObject) object;
	}
	
	public abstract void activate(NGECore core, CreatureObject player);
	public abstract void complete(NGECore core, CreatureObject player);
	public abstract void abort(NGECore core, CreatureObject player);
	public abstract void update(NGECore core, CreatureObject player);
	
	public long getStartTime() { return startTime; }
	
	public boolean isActivated() { return activated; }
	public void setActive(boolean isActive) { this.activated = isActive; }
	
	public MissionObject getMissionObject() { return parent; }
	
	public int getObjectivePhase() { return objectivePhase; }
	public void setObjectivePhase(int phase) { this.objectivePhase = phase; }
	
	public byte[] getBytes() {
		return new byte[] { };
	}
	
}
