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
	 * TU = Text You
	 * TT = Text Target
	 * TO = Text Object
	 * DI/DF = Decimal Value
	 */
	
	private byte displayFlag = 1;
	
	private String stfFile = "";
	private String stfLabel = "";

	private long tuObjectId;
	private String tuStfFile = "";
	private String tuStfLabel = "";
	private String tuCustomString = "";
	
	private long ttObjectId;
	private String ttStfFile = "";
	private String ttStfLabel = "";
	private String ttCustomString = "";
	
	private long toObjectId;
	private String toStfFile = "";
	private String toStfLabel = "";
	private String toCustomString = "";
	
	private int diInteger;
	private float dfFloat;
	
	// TODO: Add more constructors
	
	public ProsePackage(String stfFile, String stfLabel) {
		this.stfFile = stfFile;
		this.stfLabel = stfLabel;
	}
	
	public ProsePackage(String stfFile, String stfLabel, long tuObjectId, String tuStfFile, String tuStfLabel, String tuCustomString) {
		this.stfFile = stfFile;
		this.stfLabel = stfLabel;
		this.tuObjectId = tuObjectId;
		this.tuStfFile = tuStfFile;
		this.tuStfLabel = tuStfLabel;
		this.tuCustomString = tuCustomString;
	}

	public byte getDisplayFlag() {
		return displayFlag;
	}
	
	public void setDisplayFlag(byte displayFlag) {
		this.displayFlag = displayFlag;
	}

	public String getStfFile() {
		return stfFile;
	}

	public void setStfFile(String stfFile) {
		this.stfFile = stfFile;
	}

	public String getStfLabel() {
		return stfLabel;
	}

	public void setStfLabel(String stfLabel) {
		this.stfLabel = stfLabel;
	}

	public long getTuObjectId() {
		return tuObjectId;
	}

	public void setTuObjectId(long tuObjectId) {
		this.tuObjectId = tuObjectId;
	}

	public String getTuStfFile() {
		return tuStfFile;
	}

	public void setTuStfFile(String tuStfFile) {
		this.tuStfFile = tuStfFile;
	}

	public String getTuStfLabel() {
		return tuStfLabel;
	}

	public void setTuStfLabel(String tuStfLabel) {
		this.tuStfLabel = tuStfLabel;
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

	public String getTtStfFile() {
		return ttStfFile;
	}

	public void setTtStfFile(String ttStfFile) {
		this.ttStfFile = ttStfFile;
	}

	public String getTtStfLabel() {
		return ttStfLabel;
	}

	public void setTtStfLabel(String ttStfLabel) {
		this.ttStfLabel = ttStfLabel;
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

	public String getToStfFile() {
		return toStfFile;
	}

	public void setToStfFile(String toStfFile) {
		this.toStfFile = toStfFile;
	}

	public String getToStfLabel() {
		return toStfLabel;
	}

	public void setToStfLabel(String toStfLabel) {
		this.toStfLabel = toStfLabel;
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
