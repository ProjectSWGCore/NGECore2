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
package resources.z.exp.objects.cell;

import org.apache.mina.core.buffer.IoBuffer;

import resources.z.exp.objects.Baseline;
import resources.z.exp.objects.object.BaseObject;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent
public class CellObject extends BaseObject {
	
	@NotPersistent
	CellMessageBuilder messageBuilder;
	
	public CellObject(long objectID, Planet planet, int cellNumber) { 
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(0, 0, 0, 1), "object/cell/shared_cell.iff");
		setCellNumber(cellNumber);
	}
	
	public CellObject(long objectID, Planet planet) { 
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(0, 0, 0, 1), "object/cell/shared_cell.iff");
	}
	
	public CellObject() { 
		super();
	}
	
	@Override
	public void initializeBaselines() {
		super.initializeBaselines();
		initializeBaseline(8);
		initializeBaseline(9);
	}
	
	@Override
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		baseline.put("4", (byte) 1); // Unknown
		baseline.put("cellNumber", 0);
		return baseline;
	}
	
	@Override
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		baseline.put("2", (long) 0); // Unknown
		baseline.put("3", (long) 0); // Unknown
		return baseline;
	}
	
	@Override
	public Baseline getBaseline8() {
		Baseline baseline = super.getBaseline8();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline9() {
		Baseline baseline = super.getBaseline9();
		return baseline;
	}
	
	public int getCellNumber() {
		return (int) baseline3.get("cellNumber");
	}
	
	public void setCellNumber(int cellNumber) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("cellNumber", cellNumber);
		}
		
		getContainer().notifyObservers(buffer, false);
	}
	
	@Override
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		getContainer().notifyObservers(buffer, false);
	}
	
	@Override
	public CellMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null) {
				messageBuilder = new CellMessageBuilder();
			}
			
			return messageBuilder;
		}
	}
	
	@Override
	public void sendBaselines(Client destination) {
		if (destination != null && destination.getSession() != null) {
			//destination.getSession().write(baseline3.getBaseline());
			//destination.getSession().write(baseline6.getBaseline());
			//destination.getSession().write(baseline8.getBaseline());
			//destination.getSession().write(baseline9.getBaseline());
		}
	}
	
}
