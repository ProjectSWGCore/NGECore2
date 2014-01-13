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
package services.retro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import main.NGECore;

/*
 * Very often we will need to make changes to existing objects when the server
 * boots up.  This is especially the case when fixes or new features are
 * added that affect already-existing objects.  To make the changes retroactive
 * we can use this service to make it more tidy.  Add to the list of
 * modifications in the relevant service's constructor, or in this one.
 */
public class RetroService implements INetworkDispatch {
	
	private NGECore core;
	
	private List<IRetroModification> modifications;
	
	public RetroService(NGECore core) {
		this.core = core;
		modifications = new ArrayList<IRetroModification>();
		
		/*
		core.retroService.addModification(new IRetroModification() {
			
			public void modify(NGECore core) {
				
			}
			
		});
		*/
	}
	
	public void addModification(IRetroModification modification) {
		modifications.add(modification);
	}
	
	public void run() {
		for (IRetroModification modification : modifications) {
			modification.modify(core);
		}
	}
	
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
	}
	
	public void shutdown() {
		
	}
	
}
