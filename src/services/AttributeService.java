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

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.mina.core.buffer.SimpleBufferAllocator;

import main.NGECore;
import protocol.swg.AttributeListMessage;
import resources.objects.creature.CreatureObject;
import engine.resources.common.Stf;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

@SuppressWarnings("unused")

public class AttributeService implements INetworkDispatch {

	private NGECore core;
	public SimpleBufferAllocator bufferPool = new SimpleBufferAllocator();

	public AttributeService(NGECore core) {
		this.core = core;
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
	public void handleGetAttributes(SWGObject target, SWGObject requester) {
		
		if(target.getAttributes().size() == 0)
			return;
		
		if(requester.getClient() == null || requester.getClient().getSession() == null)
			return;
		
		// Color collection used in attribute based on whether collection is complete or not
		if(target.getAttachment("CollectionItemName") != null)
		{
			String collectionName = new Stf("@collection_n:" + (String) target.getAttachment("AddToCollection")).getStfValue().replace(":", " -");
			
			if(core.collectionService.isComplete((CreatureObject) requester, (String) target.getAttachment("CollectionItemName")))
			{
				target.getAttributes().put("@obj_attr_n:collection_name", "\\#FF0000 " + collectionName); 
			}
			else target.getAttributes().put("@obj_attr_n:collection_name", "\\#00FF00 " + collectionName); 
		}
		
		AttributeListMessage message = new AttributeListMessage(target, bufferPool);
		requester.getClient().getSession().write(message.serialize());
	}

}
