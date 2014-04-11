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
package resources.objects.building;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import resources.objects.cell.CellObject;
import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;
import resources.objects.tangible.TangibleObject;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.NotPersistent;
import engine.clients.Client;
import engine.resources.objects.IPersistent;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Entity(version=0)
public class BuildingObject extends TangibleObject implements IPersistent {
	
	@NotPersistent
	private BuildingMessageBuilder messageBuilder;
	@NotPersistent
	private Transaction txn;

	public BuildingObject() {
		super();
		messageBuilder = new BuildingMessageBuilder(this);
	}

	public BuildingObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, Template, position, orientation);
		messageBuilder = new BuildingMessageBuilder(this);
	}
	
	public CellObject getCellByCellNumber(final int cellNumber) {
		
		final AtomicReference<CellObject> ref = new AtomicReference<CellObject>();
		
		synchronized(objectMutex) {
			this.viewChildren(this, true, false, (obj) -> {
				if(obj instanceof CellObject && ((CellObject) obj).getCellNumber() == cellNumber) 
					ref.set((CellObject) obj);
			});
		}
		
		return ref.get();
		
	}

	@Override
	public void sendBaselines(Client destination) {
		
		if(destination == null || destination.getSession() == null) {
			System.out.println("NULL session");
			return;
		}
		
		destination.getSession().write(messageBuilder.buildBaseline3());
		destination.getSession().write(messageBuilder.buildBaseline6());
		destination.getSession().write(messageBuilder.buildBaseline8());
		destination.getSession().write(messageBuilder.buildBaseline9());

	}

	public Transaction getTransaction() { return txn; }
	
	public void createTransaction(Environment env) { 
		txn = env.beginTransaction(null, null); 
		txn.setLockTimeout(500, TimeUnit.MILLISECONDS);
	}

}
