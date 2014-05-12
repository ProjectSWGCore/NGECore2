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
package resources.objects.craft;

import java.io.Serializable;

import org.apache.mina.core.buffer.IoBuffer;

import resources.objects.ObjectMessageBuilder;

import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent
public class DraftSchematic extends SWGObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private transient ObjectMessageBuilder messageBuilder = null;

	public DraftSchematic(long objectID, Planet planet, String template, Point3D position, Quaternion orientation){
		super(objectID, planet, position, orientation, template);
	}

	@Override
	public void sendBaselines(Client arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void initAfterDBLoad() {
		super.init();
	}
	
	public ObjectMessageBuilder getMessageBuilder() {
		return messageBuilder;
	}
	
	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		// TODO Auto-generated method stub
		
	}
	
}
