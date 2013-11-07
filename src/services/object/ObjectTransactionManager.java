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
package services.object;

import java.util.Collection;
import java.util.Iterator;

import com.sleepycat.je.Transaction;

import engine.resources.objects.IPersistent;
import engine.resources.objects.SWGObject;


/**
 * This Class is used to periodically save all Objects using transactions to disk.
 * Remember that you still need commit transactions manually whenever an object using transactions is removed from zone i.e. when a player logs out.
 * @author Light
 */
public class ObjectTransactionManager implements Runnable {

	private ObjectService objService;
	private int cycleTime;

	/**
	 * 
	 * @param objService ObjectService
	 * @param cycleTime Time between each full object save in ms.
	 */
	public ObjectTransactionManager(ObjectService objService, int cycleTime) {
		
		this.objService = objService;
		this.cycleTime = cycleTime;
		
	}
	
	
	@Override
	public void run() {

		try {
			
			Thread.sleep(cycleTime);
							
			Collection<SWGObject> swgObjects = objService.getObjectList().values();
			
			synchronized(swgObjects) {
				
			Iterator<SWGObject> it = swgObjects.iterator();
				
				while(it.hasNext()) {
						
					SWGObject obj = it.next();
						
					if(obj instanceof IPersistent && !obj.isInSnapshot() && obj.isPersistent()) {
							
						if(((IPersistent) obj).getTransaction() == null)
							continue;
						
						if(!((IPersistent) obj).getTransaction().isValid())
							continue;
							
						Transaction txn = ((IPersistent) obj).getTransaction();
						txn.commitSync();
							
					}
						
				}
			
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}


