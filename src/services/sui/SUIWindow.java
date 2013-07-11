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

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.python.core.PyObject;

import engine.resources.objects.SWGObject;

public class SUIWindow {
	
	private String script;
	private SWGObject owner;
	private int windowId;
	private SWGObject rangeObject;
	private float maxDistance = 0;
	private Vector<SUIWindowComponent> components = new Vector<SUIWindowComponent>();
	private Map<Integer, PyObject> callbacks = new ConcurrentHashMap<Integer, PyObject>();
	
	
	public SUIWindow(String script, SWGObject owner, int windowId, SWGObject rangeObject, float maxDistance) {
		
		this.setScript(script);
		this.setOwner(owner);
		this.windowId = windowId;
		this.setRangeObject(rangeObject);
		this.setMaxDistance(maxDistance);
		
	}
	
	public void clearDataSource(String name) {
		
		SUIWindowComponent component = new SUIWindowComponent();
		component.setType((byte) 1);
		
		component.getNarrowParams().add(name);
		
		components.add(component);
		
	}
	
	public void setProperty(String name, String value) {
		
		SUIWindowComponent component = new SUIWindowComponent();
		component.setType((byte) 3);
		
		for(String str : name.split(":")) {
			component.getNarrowParams().add(str);
		}
		
		component.getWideParams().add(value);
		
		components.add(component);
		
	}

	public void addDataItem(String name, String value) {
		
		SUIWindowComponent component = new SUIWindowComponent();
		component.setType((byte) 4);
		
		for(String str : name.split(":")) {
			component.getNarrowParams().add(str);
		}
		
		component.getWideParams().add(value);
		
		components.add(component);
		
	}
	
	public void addHandler(int eventId, String source, byte trigger, Vector<String> returnParams, PyObject handleFunc) {
		
		SUIWindowComponent component = new SUIWindowComponent();
		component.setType((byte) 5);
		
		component.getNarrowParams().add(source);
		
		component.getNarrowParams().add(new String(new byte[] { trigger }));
		component.getNarrowParams().add("handleSUI");
		
		for(String returnParam : returnParams) {
			
			for(String str : returnParam.split(":")) {
				component.getNarrowParams().add(str);
			}
			
		}

		components.add(component);
		callbacks.put(eventId, handleFunc);

	}
	
	public void addDataSource(String name, String value) {
		
		SUIWindowComponent component = new SUIWindowComponent();
		component.setType((byte) 6);
		
		for(String str : name.split(":")) {
			component.getNarrowParams().add(str);
		}
		
		component.getWideParams().add(value);
		
		components.add(component);
		
	}


	public int getWindowId() {
		return windowId;
	}
	
	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public SWGObject getOwner() {
		return owner;
	}

	public void setOwner(SWGObject owner) {
		this.owner = owner;
	}

	public SWGObject getRangeObject() {
		return rangeObject;
	}

	public void setRangeObject(SWGObject rangeObject) {
		this.rangeObject = rangeObject;
	}

	public float getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(float maxDistance) {
		this.maxDistance = maxDistance;
	}
	
	public Vector<SUIWindowComponent> getComponents() {
		return components;
	}

	public void setComponents(Vector<SUIWindowComponent> components) {
		this.components = components;
	}

	public PyObject getFunctionByEventId(int eventId) {
		return callbacks.get(eventId);
	}
	
	public enum Trigger {;
		public static byte TRIGGER_UPDATE = 4;
		public static byte TRIGGER_OK = 9;
		public static byte TRIGGER_CANCEL = 10;

	}


}
