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
package services;

import java.util.Collections;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import main.NGECore;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class TreasureService implements INetworkDispatch {
	
	@SuppressWarnings("unused")
	private NGECore core;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private Vector<SWGObject> allRequesters = new Vector<SWGObject>();
	
	public TreasureService(NGECore core) {
		this.core = core;	
		
		scheduleTreasureService();
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
			
	}
	
	@SuppressWarnings("unused")
	public void scheduleTreasureService(){
		
		final ScheduledFuture<?> task = scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				try {
					ServiceProcessing();	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}, 10, 5000, TimeUnit.MILLISECONDS);
	}
	
	
	public void ServiceProcessing(){
		synchronized(allRequesters){
			for (SWGObject requester : allRequesters){
				if (requester!=null)
					handleRequester(requester);
			}	
			allRequesters.removeAll(Collections.singleton(null));
		}
		
	}
	
	public void handleRequester(SWGObject requester){
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
