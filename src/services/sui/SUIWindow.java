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
	private Map<Integer, SUICallback> javaCallbacks = new ConcurrentHashMap<Integer, SUICallback>();
	private Vector<SUIListBoxItem> menuItems = new Vector<SUIListBoxItem>();
	private Vector<SUITableItem> tableItems = new Vector<SUITableItem>();
	
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
	
	public void addHandler(int eventId, String source, byte trigger, Vector<String> returnParams, SUICallback handleFunc) {
		
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
		javaCallbacks.put(eventId, handleFunc);

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
	
	public void addTableDataSource(String name, String value) {
		
		SUIWindowComponent component = new SUIWindowComponent();
		component.setType((byte) 8); // using 6 (addDataSource) doesn't allow setting of column titles in tables
		
		for(String str : name.split(":")) {
			component.getNarrowParams().add(str);
		}
		
		component.getWideParams().add(value);
		
		components.add(component);
		
	}
	
	public void addListBoxMenuItem(String itemName, long objectId) {
		SUIListBoxItem menuItem = new SUIListBoxItem(itemName, objectId);
		
		int index = menuItems.size();
		
		addDataItem("List.dataList:Name", String.valueOf(index));
		setProperty("List.dataList." + index + ":Text", itemName);
		
		menuItems.add(menuItem);
	}
	
	public void addTableColumn(String itemName, String type) {
		int index = tableItems.size();
		SUITableItem item = new SUITableItem(itemName, index);

        addTableDataSource("comp.TablePage.dataTable:Name", String.valueOf(index));
        setProperty("comp.TablePage.dataTable." + index + ":Label", itemName);
        setProperty("comp.TablePage.dataTable." + index + ":Type", type);
        
        tableItems.add(item);
	}
	
	public void addTableCell(String cellName, long cellObjId, int columnIndex) {

        tableItems.forEach(column -> {
        	if (column.getIndex() == columnIndex) {
        		SUITableCell cell = new SUITableCell(cellName, cellObjId, column.getCells().size());
        		addDataItem("comp.TablePage.dataTable." + columnIndex + ":Name", "data" + cell.getCellId());
        		setProperty("comp.TablePage.dataTable." + columnIndex + ".data" + cell.getCellId() + ":Value", cellName);
        		column.getCells().add(cell);
        	}
        });
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
	
	public Map<Integer, SUICallback> getJavaCallbacks() {
		return javaCallbacks;
	}

	public SUICallback getCallbackByEventId(int eventId) {
		return javaCallbacks.get(eventId);
	}

	public Vector<SUIListBoxItem> getMenuItems() {
		return menuItems;
	}
	
	public long getObjectIdByIndex(int index) {
		SUIListBoxItem item = getMenuItems().get(index);

		if(item != null)
			return item.getObjectId();
		
		return 0;
	}
	
	public long getTableObjIdByRow(int row) {
		SUITableItem item = getTableItems().get(0);
		
		if (item != null)
			return item.getCells().get(row).getObjectId();
		return 0;
	}
	
	public Vector<SUITableItem> getTableItems() {
		return tableItems;
	}

	public void setTableItems(Vector<SUITableItem> tableItems) {
		this.tableItems = tableItems;
	}

	public enum Trigger {;
		public static byte TRIGGER_UPDATE = 4;
		public static byte TRIGGER_OK = 9;
		public static byte TRIGGER_CANCEL = 10;

	}
	
	public interface SUICallback {
		
		public void process(SWGObject owner, int eventType, Vector<String> returnList);
		
	}


}
