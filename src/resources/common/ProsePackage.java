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
package resources.common;

public class ProsePackage {
	
	/*
	 * TU = Text User
	 * TT = Text Target
	 * TO = Text Object
	 * DI = Decimal Integer
	 * DF = Decimal Float
	 */
	
	private Stf stf = new Stf();
	
	private long tuObjectId = 0;
	private Stf tuStf = new Stf();
	private String tuCustomString = "";
	
	private long ttObjectId = 0;
	private Stf ttStf = new Stf();
	private String ttCustomString = "";
	
	private long toObjectId = 0;
	private Stf toStf = new Stf();
	private String toCustomString = "";
	
	private int diInteger = 0;
	private float dfFloat = 0;
	
	// TODO: Add more constructors
	
	public ProsePackage(String stfFile, String stfLabel) {
		stf.setStfFilename(stfFile);
		stf.setStfName(stfLabel);
	}
	
	public ProsePackage(String stfFile, String stfLabel, long tuObjectId, String tuStfFile, String tuStfLabel, String tuCustomString) {
		stf.setStfFilename(stfFile);
		stf.setStfName(stfLabel);
		this.tuObjectId = tuObjectId;
		tuStf.setStfFilename(tuStfFile);
		tuStf.setStfName(tuStfLabel);
		this.tuCustomString = tuCustomString;
	}
	
	/*
	 * Any parameters can be entered.
	 * 
	 * Any integers set diInteger
	 * Any floats set dfFloat
	 * Any "@stfFile:label" sets the default stf
	 * "TU" followed by objectId, "@stfFile:label" or customString
	 * "TT" followed by objectId, "@stfFile:label" or customString
	 * "TO" followed by objectId, "@stfFile:label" or customString
	 */
	public ProsePackage(Object ... objects) {
		for (Object object: objects) {
			if (object instanceof Object[]) {
				objects = (Object[]) object;
				break;
			}
		}
		
		for (int i = 0; i < objects.length; i++) {
			Object object = objects[i];
			
			if (object instanceof String) {
				String string = (String) object;
				
				if ((i + 1) < objects.length) {
					long objectId = 0;
					Stf stf = null;
					String customString = null;
					
					if (objects[i + 1] instanceof Long) {
						objectId = (Long) objects[i + 1];
					} else if (objects[i + 1] instanceof String) {
						String str = (String) objects[i + 1];
						
						if (str.startsWith("@") && str.contains(":") && !str.contains(" ")) {
							stf = new Stf(str);
						} else {
							customString = str;
						}
					}
					
					switch (string.toUpperCase())  {
						case "TU":
							tuObjectId = (objectId == 0) ? tuObjectId : objectId;
							tuStf = (stf == null) ? tuStf : stf;
							tuCustomString = (customString == null) ? tuCustomString : customString;
							continue;
						case "TT":
							ttObjectId = (objectId == 0) ? ttObjectId : objectId;
							ttStf = (stf == null) ? ttStf : stf;
							ttCustomString = (customString == null) ? ttCustomString : customString;
							continue;
						case "TO":
							toObjectId = (objectId == 0) ? toObjectId : objectId;
							toStf = (stf == null) ? toStf : stf;
							toCustomString = (customString == null) ? toCustomString : customString;
							continue;
						default:
							//
					}
				}
				
				stf.setString((String) object);
			} else if (object instanceof Integer) {
				diInteger = (Integer) object;
			} else if (object instanceof Float) {
				dfFloat = (Float) object;
			}
		}
	}
	
	public Stf getStf() {
		return stf;
	}
	
	public void setStf(Stf stf) {
		this.stf = stf;
	}
	
	public long getTuObjectId() {
		return tuObjectId;
	}
	
	public void setTuObjectId(long tuObjectId) {
		this.tuObjectId = tuObjectId;
	}
	
	public Stf getTuStf() {
		return tuStf;
	}
	
	public void setTuStf(Stf tuStf) {
		this.tuStf = tuStf;
	}
	
	public String getTuCustomString() {
		return tuCustomString;
	}
	
	public void setTuCustomString(String tuCustomString) {
		this.tuCustomString = tuCustomString;
	}
	
	public long getTtObjectId() {
		return ttObjectId;
	}
	
	public void setTtObjectId(long ttObjectId) {
		this.ttObjectId = ttObjectId;
	}
	
	public Stf getTtStf() {
		return ttStf;
	}
	
	public void setTtStf(Stf ttStf) {
		this.ttStf = ttStf;
	}
	
	public String getTtCustomString() {
		return ttCustomString;
	}
	
	public void setTtCustomString(String ttCustomString) {
		this.ttCustomString = ttCustomString;
	}
	
	public long getToObjectId() {
		return toObjectId;
	}
	
	public void setToObjectId(long toObjectId) {
		this.toObjectId = toObjectId;
	}
	
	public Stf getToStf() {
		return toStf;
	}

	public void setToStf(Stf toStf) {
		this.toStf = toStf;
	}
	
	public String getToCustomString() {
		return toCustomString;
	}
	
	public void setToCustomString(String toCustomString) {
		this.toCustomString = toCustomString;
	}
	
	public int getDiInteger() {
		return diInteger;
	}
	
	public void setDiInteger(int diInteger) {
		this.diInteger = diInteger;
	}
	
	public float getDfFloat() {
		return dfFloat;
	}
	
	public void setDfFloat(float dfFloat) {
		this.dfFloat = dfFloat;
	}
	
}
