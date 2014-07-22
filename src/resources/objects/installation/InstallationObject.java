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
package resources.objects.installation;

import java.io.Serializable;
import java.util.Vector;

import main.NGECore;
import protocol.swg.UpdatePVPStatusMessage;
import protocol.swg.UpdatePostureMessage;

import com.sleepycat.persist.model.Entity;

import engine.clients.Client;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import resources.objects.ObjectMessageBuilder;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;

@Entity(version=0)
public class InstallationObject extends TangibleObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private transient InstallationMessageBuilder messageBuilder;

	public InstallationObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String template) {
		super(objectID, planet, position, orientation, template);
		messageBuilder = new InstallationMessageBuilder(this);
	}	
	
	@Override
	public void sendBaselines(Client destination) {
		
		if(destination == null || destination.getSession() == null) {
			//System.out.println("NULL session");
			return;
		}
		
//		destination.getSession().write(messageBuilder.buildBaseline3(this));
//		destination.getSession().write(messageBuilder.buildBaseline6(this));
				
		if(destination != getClient()) {
			UpdatePVPStatusMessage upvpm = new UpdatePVPStatusMessage(getObjectID(), NGECore.getInstance().factionService.calculatePvpStatus((CreatureObject) destination.getParent(), this), getFaction());
			destination.getSession().write(upvpm.serialize());
		}
	}
	
	@Override
	public void initAfterDBLoad() {
		super.init();
		messageBuilder = new InstallationMessageBuilder(this);
		defendersList = new Vector<TangibleObject>();
	}
	
	public ObjectMessageBuilder getMessageBuilder() {
		return messageBuilder;
	}
	
}