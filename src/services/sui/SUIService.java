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
package services.sui;

import java.nio.ByteOrder;
import java.util.Map;
import java.util.Vector;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.ObjControllerMessage;
import protocol.swg.ObjectMenuSelect;
import protocol.swg.objectControllerObjects.ObjectMenuRequest;
import protocol.swg.objectControllerObjects.ObjectMenuResponse;

import resources.common.ObjControllerOpcodes;
import resources.common.Opcodes;
import resources.common.RadialOptions;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class SUIService implements INetworkDispatch {
	
	private NGECore core;

	public SUIService(NGECore core) {
		this.core = core;
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
		objControllerOpcodes.put(ObjControllerOpcodes.OBJECT_MENU_REQUEST, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);

				ObjectMenuRequest request = new ObjectMenuRequest();
				request.deserialize(data);

				SWGObject target = core.objectService.getObject(request.getTargetId());
				SWGObject owner = core.objectService.getObject(request.getCharacterId());
	
				if(target == null || owner == null)
					return;
				
				
				core.scriptService.callScript("scripts/radial/", getRadialFilename(target), "createRadial", core, owner, target, request.getRadialOptions());
				if(getRadialFilename(target).equals("default"))
					return;
	
				sendRadial(owner, target, request.getRadialOptions(), request.getRadialCount());
				
			}
			
		});
		
		swgOpcodes.put(Opcodes.ObjectSelectMenuMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				
				ObjectMenuSelect objMenuSelect = new ObjectMenuSelect();
				objMenuSelect.deserialize(data);
				
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));

				if(client == null || client.getSession() == null)
					return;
				
				SWGObject owner = client.getParent();
				SWGObject target = core.objectService.getObject(objMenuSelect.getObjectId());
				
				if(target == null || owner == null)
					return;

				core.scriptService.callScript("scripts/radial/", getRadialFilename(target), "handleSelection", core, owner, target, objMenuSelect.getSelection());

			}
			
		});
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
	public String getRadialFilename(SWGObject object) {
		
		if(object.getAttachment("radial_filename") != null)
			return (String) object.getAttachment("radial_filename");
		else 
			return "default";
		
	}
	
	public void sendRadial(SWGObject owner, SWGObject target, Vector<RadialOptions> radialOptions, byte radialCount) {
		
		ObjectMenuResponse response = new ObjectMenuResponse(owner.getObjectID(), target.getObjectID(), radialOptions, radialCount);
		
		ObjControllerMessage objController = new ObjControllerMessage(0x0B, response);
		
		if(owner.getClient() != null && owner.getClient().getSession() != null)
			owner.getClient().getSession().write(objController.serialize());
	}

}
