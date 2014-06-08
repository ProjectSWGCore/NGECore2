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
package services.gcw;

import main.NGECore;
import net.engio.mbassy.listener.Handler;
import resources.common.collidables.AbstractCollidable;
import resources.common.collidables.AbstractCollidable.EnterEvent;
import resources.common.collidables.AbstractCollidable.ExitEvent;
import resources.datatables.FactionStatus;
import resources.datatables.PvpStatus;
import resources.objects.creature.CreatureObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;

public class PvPZone {
	
	private Planet planet;
	private AbstractCollidable area;
	private NGECore core = NGECore.getInstance();

	
	public PvPZone(Planet planet, AbstractCollidable area) {
		this.planet = planet;
		this.area = area;
		area.getEventBus().subscribe(this);
	}
	
	public Planet getPlanet() {
		return planet;
	}
	
	public void setPlanet(Planet planet) {
		this.planet = planet;
	}

	public AbstractCollidable getArea() {
		return area;
	}

	public void setArea(AbstractCollidable area) {
		this.area = area;
	}
	
	public void warpBack(CreatureObject actor) {
		if (core.mountService.isMounted(actor)) {
			actor = (CreatureObject) actor.getContainer();
		}
		area.getCollisionList().remove(actor);
		core.simulationService.teleport(actor, (Point3D) actor.getAttachment("lastValidPosition"), actor.getOrientation(), actor.getParentId());
	}
	
	@Handler
	public void onEnter(EnterEvent event) {
		
		CreatureObject actor = (CreatureObject) event.object;
		
		if(!core.factionService.isPvpFaction(actor.getFaction()) || actor.getFactionStatus() == FactionStatus.OnLeave) {
			actor.sendSystemMessage("@gcw:pvp_advanced_region_not_allowed", (byte) 0);
			warpBack(actor);
			return;
		}
		
		if(actor.getLevel() < 75) {
			actor.sendSystemMessage("@gcw:pvp_advanced_region_level_low", (byte) 0);
			warpBack(actor);
			return;
		}
		
		if(actor.getPvpStatus(PvpStatus.GoingCovert) || actor.getPvpStatus(PvpStatus.GoingOvert)) {
			actor.setPvpStatus(PvpStatus.GoingCovert | PvpStatus.GoingOvert, false);
			actor.sendSystemMessage("@gcw:pvp_advanced_region_faction_type_change_cancel", (byte) 0);
		}
		
		actor.setFactionStatus(FactionStatus.SpecialForces);
		actor.updatePvpStatus();
		actor.sendSystemMessage("@gcw:pvp_advanced_region_entered", (byte) 0);
		
	}
	
	@Handler
	public void onExit(ExitEvent event) {
		
		CreatureObject actor = (CreatureObject) event.object;
		
		actor.updatePvpStatus();
		actor.sendSystemMessage("@gcw:pvp_advanced_region_exited", (byte) 0);
		
	}


}
