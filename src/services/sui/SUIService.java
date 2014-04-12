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
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.python.core.Py;
import org.python.core.PyObject;

import protocol.swg.ObjControllerMessage;
import protocol.swg.ObjectMenuSelectMessage;
import protocol.swg.SUICreatePageMessage;
import protocol.swg.SUIEventNotification;
import protocol.swg.SUIForceClosePageMessage;
import protocol.swg.SUIUpdatePageMessage;
import protocol.swg.objectControllerObjects.ObjectMenuRequest;
import protocol.swg.objectControllerObjects.ObjectMenuResponse;
import resources.common.FileUtilities;
import resources.common.ObjControllerOpcodes;
import resources.common.Opcodes;
import resources.common.RadialOptions;
import resources.objects.creature.CreatureObject;
import resources.objects.harvester.HarvesterObject;
import services.sui.SUIWindow.SUICallback;
import services.sui.SUIWindow.Trigger;
import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class SUIService implements INetworkDispatch {
	
	private NGECore core;
	private Map<Integer, SUIWindow> windowMap = new ConcurrentHashMap<Integer, SUIWindow>();

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
				
				if (target instanceof HarvesterObject){
					HarvesterObject harvester = (HarvesterObject) target;
					Vector<String> admins = harvester.getAdminList();
					Vector<String> hoppers = harvester.getHopperList();
					CreatureObject creature = (CreatureObject) core.objectService.getObject(harvester.getOwner());
					if (creature == owner && !admins.contains(owner.getCustomName())){
						admins.add(owner.getCustomName());
					}
					
					if (! admins.contains(owner.getCustomName()) && ! hoppers.contains(owner.getCustomName())){
						return; // Completely unauthorized				
					}
					
					if (! admins.contains(owner.getCustomName()) && hoppers.contains(owner.getCustomName())){
						 // authorized for hopper
						// change radialOptions to hopper access
						core.scriptService.callScript("scripts/radial/", "harvesterHopper", "createRadial", core, owner, target, request.getRadialOptions());
						sendRadial(owner, target, request.getRadialOptions(), request.getRadialCount());
						return;
					}
				}
				
				if(target.getGrandparent() != null && target.getGrandparent().getAttachment("structureAdmins") != null)
				{
					if(core.housingService.getPermissions(owner, target.getContainer()) && !getRadialFilename(target).equals("structure_management_terminal"))
					{
						core.scriptService.callScript("scripts/radial/", "moveable", "createRadial", core, owner, target, request.getRadialOptions());
						sendRadial(owner, target, request.getRadialOptions(), request.getRadialCount());
						return;
					}
				}
				
				core.scriptService.callScript("scripts/radial/", getRadialFilename(target), "createRadial", core, owner, target, request.getRadialOptions());
				if(getRadialFilename(target).equals("default"))
					return;
				
				sendRadial(owner, target, request.getRadialOptions(), request.getRadialCount());
				
			}
			
		});
		
		swgOpcodes.put(Opcodes.ObjectMenuSelectMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				
				ObjectMenuSelectMessage objMenuSelect = new ObjectMenuSelectMessage();
				objMenuSelect.deserialize(data);
				
				Client client = core.getClient(session);
				if(client == null || client.getSession() == null)
					return;
				
				SWGObject owner = client.getParent();
				SWGObject target = core.objectService.getObject(objMenuSelect.getObjectId());
				
				if(target == null || owner == null)
					return;
				
				core.scriptService.callScript("scripts/radial/", getRadialFilename(target), "handleSelection", core, owner, target, objMenuSelect.getSelection());

			}
			
		});
		
		swgOpcodes.put(Opcodes.SuiEventNotification, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				
				SUIEventNotification suiEvent = new SUIEventNotification();
				suiEvent.deserialize(data);
				
				SUIWindow window = getWindowById(suiEvent.getWindowId());
				
				if(window == null)
					return;
				
				Client client = core.getClient(session);

				if(client == null || client.getSession() == null)
					return;

				SWGObject owner = client.getParent();

				if(owner == null)
					return;

				PyObject func = window.getFunctionByEventId(suiEvent.getEventType());
				
				if(func != null)
					func.__call__(Py.java2py(owner), Py.java2py(window), Py.java2py(suiEvent.getEventType()), Py.java2py(suiEvent.getReturnList()));
				
				SUICallback callback = window.getCallbackByEventId(suiEvent.getEventType());
				
				if(callback != null)
					callback.process(owner, suiEvent.getEventType(), suiEvent.getReturnList());
				
				windowMap.remove(window.getWindowId());
				
			}

		});
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
	public String getRadialFilename(SWGObject object) {
		String serverTemplate = ("scripts/" + object.getTemplate().split("shared_" , 2)[0].replace("shared_", "") + object.getTemplate().split("shared_" , 2)[1] + ".py");
		String radialFilename = "default";
		
		if (FileUtilities.doesFileExist(serverTemplate)) {
			PyObject method = core.scriptService.getMethod("scripts/" + object.getTemplate().split("shared_" , 2)[0].replace("shared_", ""), object.getTemplate().split("shared_" , 2)[1].replace(".iff", ""), "getRadialFilename");
			
			if (method != null && method.isCallable()) {
				radialFilename = method.__call__().asString();
				
				if (radialFilename == null || radialFilename.equals("")) {
					radialFilename = "default";
				}
			}
		}
		
		if (radialFilename.equals("default") && object.getAttachment("radial_filename") != null) {
			radialFilename = (String) object.getAttachment("radial_filename");
		}
		
		return radialFilename;
	}
	
	public void sendRadial(SWGObject owner, SWGObject target, Vector<RadialOptions> radialOptions, byte radialCount) {
		
		ObjectMenuResponse response = new ObjectMenuResponse(owner.getObjectID(), target.getObjectID(), radialOptions, radialCount);
		
		ObjControllerMessage objController = new ObjControllerMessage(0x0B, response);
		
		if(owner.getClient() != null && owner.getClient().getSession() != null)
			owner.getClient().getSession().write(objController.serialize());
	}

	public Map<Integer, SUIWindow> getWindowMap() {
		return windowMap;
	}
	
	public SUIWindow getWindowById(int id) {
		if(windowMap.containsKey(id))
			return windowMap.get(id);
		return null;
	}
	
	public int createWindowId() {
		
		Random rand = new Random();
		
		int id = rand.nextInt();
		
		if(windowMap.containsKey(id))
			return createWindowId();
		
		return id;
		
	}
	
	public SUIWindow createSUIWindow(String script, SWGObject owner, SWGObject rangeObject, float maxDistance) {
		
		SUIWindow window = new SUIWindow(script, owner, createWindowId(), rangeObject, maxDistance);
		windowMap.put(window.getWindowId(), window);
		return window; 
		
	}
	
	public SUIWindow createListBox(int type, String title, String promptText, Map<Long, String> cloneData, SWGObject owner, SWGObject rangeObject, float maxDistance) {
		
		SUIWindow window = createSUIWindow("Script.listBox", owner, rangeObject, maxDistance);
		
		window.setProperty("bg.caption.lblTitle:Text", title);
		window.setProperty("Prompt.lblPrompt:Text", promptText);

		switch(type) {
			
			case ListBoxType.LIST_BOX_OK: 
				window.setProperty("btnOk:visible", "True");
				window.setProperty("btnOk:Text", "@ok");
				window.setProperty("btnCancel:visible", "False");
				break;
			case ListBoxType.LIST_BOX_OK_CANCEL: 
				window.setProperty("btnOk:visible", "True");
				window.setProperty("btnCancel:visible", "True");
				window.setProperty("btnOk:Text", "@ok");
				window.setProperty("btnCancel:Text", "@cancel");
				break;

		}
		
		window.clearDataSource("List.dataList");
		
		cloneData.entrySet().forEach(e -> window.addListBoxMenuItem(e.getValue(), e.getKey()));
		
		return window;
		
	}
	
	public SUIWindow createMessageBox(int type, String title, String promptText, SWGObject owner, SWGObject rangeObject, float maxDistance) {
		
		SUIWindow window = createSUIWindow("Script.messageBox", owner, rangeObject, maxDistance);
		
		window.setProperty("bg.caption.lblTitle:Text", title);
		window.setProperty("Prompt.lblPrompt:Text", promptText);
		
		window.setProperty("btnRevert:visible", "False");

		switch(type) {
		
			case MessageBoxType.MESSAGE_BOX_OK:
				window.setProperty("btnOk:visible", "True");
				window.setProperty("btnOk:Text", "@ok");
				window.setProperty("btnCancel:visible", "False");
				break;
			case MessageBoxType.MESSAGE_BOX_OK_CANCEL:
				window.setProperty("btnOk:visible", "True");
				window.setProperty("btnCancel:visible", "True");
				window.setProperty("btnOk:Text", "@ok");
				window.setProperty("btnCancel:Text", "@cancel");
				break;
			case MessageBoxType.MESSAGE_BOX_YES_NO:
				window.setProperty("btnOk:visible", "True");
				window.setProperty("btnCancel:visible", "True");
				window.setProperty("btnOk:Text", "@yes");
				window.setProperty("btnCancel:Text", "@no");
				break;

		}

		return window;
		
	}
	
	public SUIWindow createInputBox(int type, String title, String promptText, SWGObject owner, SWGObject rangeObject, float maxDistance) {
		SUIWindow window = createSUIWindow("Script.inputBox", owner, rangeObject, maxDistance);
		
		window.setProperty("bg.caption.lblTitle:Text", title);
		window.setProperty("Prompt.lblPrompt:Text", promptText);
		
		switch(type) {
		case InputBoxType.INPUT_BOX_OK:
			window.setProperty("btnOk:visible", "True");
			window.setProperty("btnOk:Text", "@ok");
			window.setProperty("btnCancel:visible", "False");
			window.setProperty("cmbInput:visible", "False");
			break;
		case InputBoxType.INPUT_BOX_OK_CANCEL:
			window.setProperty("btnOk:visible", "True");
			window.setProperty("btnCancel:visible", "True");
			window.setProperty("btnCancel:Text", "@cancel");
			window.setProperty("btnOk:Text", "@ok");
			window.setProperty("cmbInput:visible", "False");
		}
		return window;
	}
	
	/**
	 * Creates an Input Box SUIWindow and adds a handler that will return text inputed into text box with the callback as the specified PyObject.
	 * @param PyObject Definition that will be run when user clicks on "OK"
	 * @return SUIWindow
	 * @author Waverunner
	 */
	public SUIWindow createInputBox(int type, String title, String promptText, SWGObject owner, SWGObject rangeObject, float maxDistance, PyObject handleFunc) {
		SUIWindow window = createInputBox(type, title, promptText, owner, rangeObject, maxDistance);
		Vector<String> returnParams = new Vector<String>();
		returnParams.add("txtInput:LocalText");
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnParams, handleFunc);
		return window;
	}
	
	/**
	 * Creates an Input Box SUIWindow and adds a handler that will return text inputed into text box with the callback as the specified PyObject.
	 * @param SUICallback Callback that will be ran when user clicks on "OK"
	 * @return SUIWindow
	 * @author Waverunner
	 */
	public SUIWindow createInputBox(int type, String title, String promptText, SWGObject owner, SWGObject rangeObject, float maxDistance, SUICallback handleFunc) {
		SUIWindow window = createInputBox(type, title, promptText, owner, rangeObject, maxDistance);
		Vector<String> returnParams = new Vector<String>();
		returnParams.add("txtInput:LocalText");
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnParams, handleFunc);
		return window;
	}
	
	public void closeSUIWindow(SWGObject owner, int id) {
		
		if(owner.getClient() == null || owner.getClient().getSession() == null)
			return;
		
		windowMap.remove(id);
		
		SUIForceClosePageMessage closeMsg = new SUIForceClosePageMessage(id);
		owner.getClient().getSession().write(closeMsg.serialize());	
		
	}
	
	public void openSUIWindow(SUIWindow window) {
		
		SWGObject owner = window.getOwner();
		
		if(owner == null)
			return;
		
		if(owner.getClient() == null || owner.getClient().getSession() == null)
			return;
		
		float range;
		long rangeObjectId;
		
		if(window.getRangeObject() == null) {
			range = 0;
			rangeObjectId = 0;
		} else {
			range = window.getMaxDistance();
			rangeObjectId = window.getRangeObject().getObjectID();
		}
		
		SUICreatePageMessage create = new SUICreatePageMessage(window.getScript(), window.getWindowId(), window.getComponents(), rangeObjectId, range);
		owner.getClient().getSession().write(create.serialize());
		
	}
	
	public void updateSUIWindow(SUIWindow window) {
		
		SWGObject owner = window.getOwner();
		
		if(owner == null)
			return;
		
		if(owner.getClient() == null || owner.getClient().getSession() == null)
			return;
		
		float range;
		long rangeObjectId;
		
		if(window.getRangeObject() == null) {
			range = 0;
			rangeObjectId = 0;
		} else {
			range = window.getMaxDistance();
			rangeObjectId = window.getRangeObject().getObjectID();
		}
		
		SUIUpdatePageMessage update = new SUIUpdatePageMessage(window.getScript(), window.getWindowId(), window.getComponents(), rangeObjectId, range);
		owner.getClient().getSession().write(update.serialize());


	}
	
	public enum ListBoxType {;
		public static final int LIST_BOX_OK = 1;
		public static final int LIST_BOX_OK_CANCEL = 2;
	}

	public enum MessageBoxType {;
		public static final int MESSAGE_BOX_OK = 1;
		public static final int MESSAGE_BOX_OK_CANCEL = 2;
		public static final int MESSAGE_BOX_YES_NO = 3;
	}
	
	public enum InputBoxType {;
		public static final int INPUT_BOX_OK = 1;
		public static final int INPUT_BOX_OK_CANCEL = 2;
	}
}
