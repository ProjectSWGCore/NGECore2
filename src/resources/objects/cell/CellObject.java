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
package resources.objects.cell;

import java.io.Serializable;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.UpdateCellPermissionMessage;
import resources.objects.ObjectMessageBuilder;
import resources.objects.building.BuildingObject;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent(version=0)
public class CellObject extends SWGObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int cellNumber = 0;
	@NotPersistent
	private transient CellMessageBuilder messageBuilder;
	
	public CellObject() { 
		super();
		messageBuilder = new CellMessageBuilder(this);
	}
	
	public CellObject(long objectID, Planet planet, int cellNumber) { 
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 0, 0), "object/cell/shared_cell.iff");
		setCellNumber(cellNumber);
		messageBuilder = new CellMessageBuilder(this);
	}
	
	public CellObject(long objectID, Planet planet) { 
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 0, 0), "object/cell/shared_cell.iff");
		messageBuilder = new CellMessageBuilder(this);
	}

	public int getCellNumber() {
		synchronized(objectMutex) {
			return cellNumber;
		}
	}

	public void setCellNumber(int cellNumber) {
		synchronized(objectMutex) {
			this.cellNumber = cellNumber;
		}
	}

	@Override
	public void sendBaselines(Client destination) {
		
		if(destination == null || destination.getSession() == null) {
			System.out.println("NULL session");
			return;
		}
				
		destination.getSession().write(messageBuilder.buildBaseline3());
		destination.getSession().write(messageBuilder.buildBaseline6());
		sendPermissionMessage(destination);	
		
	}
	
	public void sendPermissionMessage(Client destination) {
		
		if(destination == null || destination.getSession() == null) {
			System.out.println("NULL session");
			return;
		}
		
		byte canEnter = (byte) (((BuildingObject) getContainer()).canEnter(destination.parent) ? 1 : 0);
		destination.getSession().write(new UpdateCellPermissionMessage(canEnter, getObjectID()).serialize());

	}

	@Override
	public void initAfterDBLoad() {
		super.init();
		messageBuilder = new CellMessageBuilder(this);
	}
	
	public ObjectMessageBuilder getMessageBuilder() {
		return messageBuilder;
	}
	
	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		// TODO Auto-generated method stub
		
	}
	
}
