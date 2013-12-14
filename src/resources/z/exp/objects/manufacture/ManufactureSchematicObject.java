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
package resources.z.exp.objects.manufacture;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.mina.core.buffer.IoBuffer;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import resources.z.exp.manufacture.Property;
import resources.z.exp.manufacture.SubList;
import resources.z.exp.manufacture.TableAndKey;
import resources.z.exp.objects.Baseline;
import resources.z.exp.objects.SWGList;
import resources.z.exp.objects.SWGMultiMap;
import resources.z.exp.objects.intangible.IntangibleObject;

@Persistent
public class ManufactureSchematicObject extends IntangibleObject {
	
	@NotPersistent
	private ManufactureSchematicMessageBuilder messageBuilder;
	
	public ManufactureSchematicObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, position, orientation, Template);
		initializeBaseline(7);
	}
	
	public ManufactureSchematicObject() { 
		super();
	}
	
	@Override
	public void initializeBaselines() {
		super.initializeBaselines();
	}
	
	@Override
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		baseline.put("totalSlots", (byte) 0);
		return baseline;
	}
	
	@Override
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		baseline.put("properties", new SWGMultiMap<String, Property>(this, 3, 5, true));
		baseline.put("schematicComplexity", 0);
		baseline.put("schematicDataSize", (float) 1);
		return baseline;
	}
	
	@Override
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		baseline.put("schematicCustomization", new byte[] { });
		baseline.put("customizationTemplate", "");
		baseline.put("schematicPrototype", "");
		baseline.put("inUse", false);
		baseline.put("filledSlots", (byte) 0);
		return baseline;
	}
	
	@Override
	public Baseline getBaseline7() {
		Baseline baseline = super.getBaseline7();
		baseline.put("slotNameList", new SWGList<TableAndKey>(this, 7, 0, false));
		baseline.put("slotContentsList", new SWGList<Integer>(this, 7, 1, false));
		baseline.put("ingredientList", new SWGList<SubList<Long>>(this, 7, 2, false));
		baseline.put("quantityList", new SWGList<SubList<Integer>>(this, 7, 3, false));
		baseline.put("qualityList", new SWGList<Float>(this, 7, 4, false));
		baseline.put("cleanSlotList", new SWGList<Integer>(this, 7, 5, false));
		baseline.put("slotIndexList", new SWGList<Integer>(this, 7, 6, false));
		baseline.put("ingredientsCounter", (byte) 0);
		baseline.put("experimentationNameList", new SWGList<TableAndKey>(this, 7, 8, false));
		baseline.put("currentExperimentationValueList", new SWGList<Float>(this, 7, 9, false));
		baseline.put("experimentationOffsetList", new SWGList<Float>(this, 7, 10, false));
		baseline.put("bluebarList", new SWGList<Float>(this, 7, 11, false));
		baseline.put("maxExperimentationList", new SWGList<Float>(this, 7, 12, false));
		baseline.put("customizationNameList", new SWGList<String>(this, 7, 13, false));
		baseline.put("palleteSelectionList", new SWGList<Integer>(this, 7, 14, false));
		baseline.put("palleteStartIndexList", new SWGList<Integer>(this, 7, 15, false));
		baseline.put("palleteEndIndexList", new SWGList<Integer>(this, 7, 16, false));
		baseline.put("customizationCounter", (byte) 0);
		baseline.put("riskFactor", (float) 0);
		baseline.put("objectTemplateCustomizationList", new SWGList<String>(this, 7, 19, false));
		baseline.put("ready", true);
		return baseline;
	}
	
	@Override
	public Baseline getBaseline8() {
		Baseline baseline = super.getBaseline8();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline9() {
		Baseline baseline = super.getBaseline9();
		return baseline;
	}
	
	public void setQuantity(int quantity) {
		setGenericInt(quantity);
	}
	
	public void incrementQuantity(int increase) {
		incrementGenericInt(increase);
	}
	
	public void decrementQuantity(int decrease) {
		decrementGenericInt(decrease);
	}
	
	@SuppressWarnings("unchecked")
	public SWGMultiMap<String, Property> getPropertiesMap() {
		return (SWGMultiMap<String, Property>) baseline3.get("properties");
	}
	
	public Collection<Property> getPropertiesForFile(String stfFile) {
		synchronized(objectMutex) {
			if (getPropertiesMap().containsKey(stfFile)) {
				return getPropertiesMap().get(stfFile);
			} else {
				return new ArrayList<Property>();
			}
		}
	}
	
	public Collection<Property> getAllProperties() {
		synchronized(objectMutex) {
			return getPropertiesMap().values();
		}
	}
	
	public boolean containsKey(String stfFile, String key) {
		synchronized(objectMutex) {
			for (Property property : getPropertiesMap().get(stfFile)) {
				if (property.getKey().equals(key)) {
					return true;
				}
			}
			
			return false;
		}
	}
	
	public boolean containsKey(String key) {
		synchronized(objectMutex) {
			for (Property property : getPropertiesMap().values()) {
				if (property.getKey().equals(key)) {
					return true;
				}
			}
			
			return false;
		}
	}
	
	public Property getProperty(String stfFile, String key) {
		synchronized(objectMutex) {
			for (Property property: getPropertiesMap().get(stfFile)) {
				if (property.getKey().equals(key)) {
					return property;
				}
			}
			
			return null;
		}
	}
	
	public Property getProperty(String key) {
		synchronized(objectMutex) {
			for (Property property: getPropertiesMap().values()) {
				if (property.getKey().equals(key)) {
					return property;
				}
			}
			
			return null;
		}
	}
	
	public void addProperty(String stfFile, String key, float value) {
		if (!containsKey(stfFile, key)) {
			getPropertiesMap().put(stfFile, new Property(key, value));
		}
	}
	
	public void setProperty(String stfFile, String key, float value) {
		if (getPropertiesMap().containsKey(stfFile)) {
			for (Property property : getPropertiesMap().get(stfFile)) {
				if (property.getKey().equals(key)) {
					property.setValue(value);
					getPropertiesMap().replaceValues(stfFile, getPropertiesMap().get(stfFile));
					return;
				}
			}
		}
	}
	
	public void removeProperty(String stfFile, String key) {
		if (containsKey(stfFile, key)) {
			getPropertiesMap().remove(stfFile, getProperty(key));
		}
	}
	
	public void removeFile(String stfFile) {
		getPropertiesMap().removeAll(stfFile);
	}
	
	public int getSchematicComplexity() {
		synchronized(objectMutex) {
			return (int) baseline3.get("schematicComplexity");
		}
	}
	
	public void setSchematicComplexity(int schematicComplexity) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("schematicComplexity", schematicComplexity);
		}
		
		notifyClients(buffer, false);
	}
	
	public float getSchematicDataSize() {
		synchronized(objectMutex) {
			return (float) baseline3.get("schematicDataSize");
		}
	}
	
	public void setSchematicDataSize(float schematicDataSize) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("schematicDataSize", schematicDataSize);
		}
		
		notifyClients(buffer, false);
	}
	
	public byte[] getSchematicCustomization() {
		synchronized(objectMutex) {
			return (byte[]) baseline6.get("schematicCustomization");
		}
	}
	
	public void setSchematicCustomization(byte[] schematicCustomization) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("schematicCustomization", schematicCustomization);
		}
		
		notifyClients(buffer, false);
	}
	
	public String getCustomizationTemplate() {
		synchronized(objectMutex) {
			return (String) baseline6.get("customizationTemplate");
		}
	}
	
	public void setCustomizationTemplate(String customizationTemplate) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("customizationTemplate", customizationTemplate);
		}
		
		notifyClients(buffer, false);
	}
	
	public String getSchematicTemplate() {
		synchronized(objectMutex) {
			return (String) baseline6.get("schematicTemplate");
		}
	}
	
	public void setSchematicTemplate(String schematicTemplate) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("schematicTemplate", schematicTemplate);
		}
		
		notifyClients(buffer, false);
	}
	
	public boolean inUse() {
		synchronized(objectMutex) {
			return (boolean) baseline6.get("inUse");
		}
	}
	
	public void setInUse(boolean inUse) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("inUse", inUse);
		}
		
		notifyClients(buffer, false);
	}
	
	public void toggleInUse() {
		setInUse(!inUse());
	}
	
	public byte getTotalSlots() {
		synchronized(objectMutex) {
			return (byte) otherVariables.get("totalSlots");
		}
	}
	
	public void setTotalSlots(byte totalSlots) {
		synchronized(objectMutex) {
			otherVariables.set("totalSlots", totalSlots);
		}
	}
	
	public byte getFilledSlots() {
		synchronized(objectMutex) {
			return (byte) baseline6.get("filledSlots");
		}
	}
	
	public void setFilledSlots(byte filledSlots) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("filledSlots", filledSlots);
		}
		
		notifyClients(buffer, false);
	}
	
	public void incrementFilledSlots(int increase) {
		setFilledSlots(((byte) (getFilledSlots() + ((byte) increase))));
	}
	
	public void decrementFilledSlots(int decrease) {
		setFilledSlots(((byte) (((getFilledSlots() - ((byte) decrease)) < 0) ? ((byte) 0) : (getFilledSlots() - ((byte) decrease)))));
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<TableAndKey> getSlotNameList() {
		return (SWGList<TableAndKey>) baseline7.get("slotNameList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Integer> getSlotContentsList() {
		return (SWGList<Integer>) baseline7.get("slotContentsList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<SubList<Long>> getIngredientList() {
		return (SWGList<SubList<Long>>) baseline7.get("ingredientList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<SubList<Integer>> getQuantityList() {
		return (SWGList<SubList<Integer>>) baseline7.get("quantityList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Float> getQualityList() {
		return (SWGList<Float>) baseline7.get("quantityList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Integer> getCleanSlotList() {
		return (SWGList<Integer>) baseline7.get("cleanSlotList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Integer> getSlotIndexList() {
		return (SWGList<Integer>) baseline7.get("slotIndexList");
	}
	
	public byte getIngredientsCounter() {
		return (byte) baseline7.get("ingredientsCounter");
	}
	
	public void setIngredientsCounter(byte ingredientsCounter) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline7.set("ingredientsCounter", ingredientsCounter);
		}
		
		if (getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(buffer);
		}
	}
	
	public void incrementIngredientsCounter(int increase) {
		setIngredientsCounter((byte) (getIngredientsCounter() + ((byte) increase)));
	}
	
	public void decrementIngredientsCoutner(int decrease) {
		byte ingredientsCounter = getIngredientsCounter();
		setIngredientsCounter(((ingredientsCounter < 0) ? 0 : ((byte) (ingredientsCounter - ((byte) decrease)))));
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<TableAndKey> getExperimentationNameList() {
		return (SWGList<TableAndKey>) baseline7.get("experimentationNameList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Float> getCurrentExperimentationValueList() {
		return (SWGList<Float>) baseline7.get("currentExperimentationValueList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Float> getExperimentationOffsetList() {
		return (SWGList<Float>) baseline7.get("experimentationOffsetList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Float> getBlueBarList() {
		return (SWGList<Float>) baseline7.get("blueBarList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Float> getMaxExperimentationList() {
		return (SWGList<Float>) baseline7.get("maxExperimentationList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<String> getCustomizationNameList() {
		return (SWGList<String>) baseline7.get("customizationNameList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Integer> getPalleteSelectionList() {
		return (SWGList<Integer>) baseline7.get("palleteSelectionList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Integer> getPalleteStartIndexList() {
		return (SWGList<Integer>) baseline7.get("palleteStartIndexList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Integer> getPalleteEndIndexList() {
		return (SWGList<Integer>) baseline7.get("palleteEndIndexList");
	}
	
	public byte getCustomizationCounter() {
		synchronized(objectMutex) {
			return (byte) baseline7.get("customizationCounter");
		}
	}
	
	public void setCustomizationCounter(byte customizationCounter) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline7.set("customizationCounter", customizationCounter);
		}
		
		if (getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(buffer);
		}
	}
	
	public void incrementCustomizationCounter(int increase) {
		
	}
	
	public void decrementCustomizationCounter(int decrease) {
		
	}
	
	public float getRiskFactor() {
		synchronized(objectMutex) {
			return (float) baseline7.get("riskFactor");
		}
	}
	
	public void setRiskFactor(float riskFactor) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline7.set("riskFactor", riskFactor);
		}
		
		if (getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(buffer);
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<String> getObjectTemplateCustomizationList() {
		return (SWGList<String>) baseline7.get("objectTemplateCustomizationList");
	}
	
	public boolean isReady() {
		synchronized(objectMutex) {
			return (boolean) baseline7.get("isReady");
		}
	}
	
	public void setReady(boolean ready) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline7.set("ready", ready);
		}
		
		if (getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(buffer);
		}
	}
	
	public void toggleReady() {
		setReady(!isReady());
	}
	
	@Override
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		notifyObservers(buffer, notifySelf);
	}
	
	@Override
	public ManufactureSchematicMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null) {
				messageBuilder = new ManufactureSchematicMessageBuilder(this);
			}
			
			return messageBuilder;
		}
	}
	
	public void sendBaseline7(Client destination) {
		destination.getSession().write(baseline7.getBaseline());
	}
	
	@Override
	public void sendBaselines(Client destination) {
		if (destination != null && destination.getSession() != null) {
			destination.getSession().write(baseline3.getBaseline());
			destination.getSession().write(baseline6.getBaseline());
			destination.getSession().write(baseline8.getBaseline());
			destination.getSession().write(baseline9.getBaseline());
		}
	}
	
	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		switch (viewType) {
			case 1:
			case 4:
			case 7:
				if (getGrandparent().getClient() != null) {
					buffer = getBaseline(viewType).createDelta(updateType, buffer.array());
					getGrandparent().getClient().getSession().write(buffer);
				}
				
				return;
			case 3:
			case 6:
			case 8:
			case 9:
				notifyObservers(getBaseline(viewType).createDelta(updateType, buffer.array()), true);
			default:
				return;
		}
	}
	
}
