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

import java.util.Map;

import protocol.swg.LaunchBrowserMessage;
import resources.objects.creature.CreatureObject;
import main.NGECore;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class BrowserService implements INetworkDispatch {
	
	@SuppressWarnings("unused")
	private NGECore core;
	
	public BrowserService(NGECore core) {
		this.core = core;
	}
	/*
	 * This method will minimise the game and open the default desktop browser of the CreatureObject with the given URL.
	 * This is not to be confused with the SWG in-game browser.
	 */
	public void sendBrowserWindow(CreatureObject creature, String URL) {
		URL = URL.toLowerCase();
		
		if( URL.contains("http://"))
			URL.replace("http://", "");
		
		LaunchBrowserMessage launchBrowserMessage = new LaunchBrowserMessage(URL);
		creature.getClient().getSession().write(launchBrowserMessage.serialize());
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}
	
	@Override
	public void shutdown() {
		
	}
	
}
