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
package resources.objects.tool;

import java.util.Vector;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import resources.objects.creature.CreatureObject;
import resources.objects.resource.GalacticResource;
import resources.objects.resource.ResourceConcentration;
import resources.objects.tangible.TangibleObject;

/** 
 * @author Charon 
 */

@Persistent(version=0)
public class SurveyTool extends TangibleObject{
	
	private byte toolType;
	private GalacticResource surveyResource;
	private CreatureObject user;
	private Long tanoID;
	private byte SurveyRangeSetting;
	
	@NotPersistent
	private String surveyEffectString;
	@NotPersistent
	private String sampleEffectString;
	@NotPersistent
	private boolean currentlySurveying;
	@NotPersistent
	private boolean currentlySampling;
	@NotPersistent
	private boolean currentlyCoolingDown;
	@NotPersistent
	private boolean exceptionalState;
	@NotPersistent
	private boolean recoveryMode;
	@NotPersistent
	private Long lastSurveyTime;
	@NotPersistent
	private Long lastSampleTime;
	@NotPersistent
	private Long recoveryTime;
	
	public static byte MineralSurveyDevice	           = 1;
	public static byte ChemicalSurveyDevice	           = 2;
	public static byte FloraSurveyTool	               = 3;
	public static byte GasPocketSurveyDevice	       = 4;
	public static byte WaterSurveyDevice	           = 5;
	public static byte WindCurrentSurveyingTool	       = 6;
	public static byte AmbientSolarEnergySurveyingTool = 7;
	public static byte CompleteResourceSurveyDevice	   = 8;

	public SurveyTool() {
		super();
		this.exceptionalState = false;
	}
	
	public SurveyTool(long objectID, Planet planet, String template, Point3D position, Quaternion orientation){
		super(objectID, planet, template, position, orientation);
		surveyEffectString = "";
		sampleEffectString = "";
		this.tanoID = objectID; 
		this.exceptionalState = false;
		this.recoveryMode = false;
		switch (template) {
			case "object/tangible/survey_tool/shared_survey_tool_mineral.iff"   : 
				toolType = 1; 
				surveyEffectString = "clienteffect/survey_tool_mineral.cef";
				sampleEffectString = "clienteffect/survey_sample_mineral.cef";
				break; 
			case "object/tangible/survey_tool/shared_survey_tool_inorganic.iff" : 
				toolType = 2; 
				surveyEffectString = "clienteffect/survey_tool_lumber.cef";
				sampleEffectString = "clienteffect/survey_sample_lumber.cef";
				break; 
			case "object/tangible/survey_tool/shared_survey_tool_lumber.iff"    : 
				toolType = 3; 
				surveyEffectString = "clienteffect/survey_tool_lumber.cef";
				sampleEffectString = "clienteffect/survey_sample_lumber.cef";
				break; 
			case "object/tangible/survey_tool/shared_survey_tool_gas.iff"       : 
				toolType = 4; 
				surveyEffectString = "clienteffect/survey_tool_gas.cef";
				sampleEffectString = "clienteffect/survey_sample_gas.cef";				
				break; 
			case "object/tangible/survey_tool/shared_survey_tool_moisture.iff"    : 
				toolType = 5; 
				surveyEffectString = "clienteffect/survey_tool_liquid.cef";
				sampleEffectString = "clienteffect/survey_sample_liquid.cef";
				break; 
			case "object/tangible/survey_tool/shared_survey_tool_wind.iff"      : 
				toolType = 6; 
				surveyEffectString = "clienteffect/survey_tool_gas.cef";
				sampleEffectString = "clienteffect/survey_sample_gas.cef";				
				break; 
			case "object/tangible/survey_tool/shared_survey_tool_solar.iff"     : 
				toolType = 7; 
				surveyEffectString = "clienteffect/survey_tool_moisture.cef";
				sampleEffectString = "clienteffect/survey_sample_moisture.cef";				
				break; 
			case "object/tangible/survey_tool/shared_survey_tool_all.iff"       : 
				toolType = 8; 
				surveyEffectString = "clienteffect/survey_tool_liquid.cef";
				sampleEffectString = "clienteffect/survey_sample_liquid.cef";
				break; 
			default: toolType = -1;
		}
	}
	
	public SurveyTool(String template, Long tanoID) {
		super();
		surveyEffectString = "";
		sampleEffectString = "";
		this.tanoID = tanoID; 
		this.exceptionalState = false;
		switch (template) {
			case "object/tangible/survey_tool/shared_survey_tool_mineral.iff"   : 
				toolType = 1; 
				surveyEffectString = "clienteffect/survey_tool_mineral.cef";
				sampleEffectString = "clienteffect/survey_sample_mineral.cef";
				break; 
			case "object/tangible/survey_tool/shared_survey_tool_inorganic.iff" : 
				toolType = 2; 
				surveyEffectString = "clienteffect/survey_tool_lumber.cef";
				sampleEffectString = "clienteffect/survey_sample_lumber.cef";
				break; 
			case "object/tangible/survey_tool/shared_survey_tool_lumber.iff"    : 
				toolType = 3; 
				surveyEffectString = "clienteffect/survey_tool_lumber.cef";
				sampleEffectString = "clienteffect/survey_sample_lumber.cef";
				break; 
			case "object/tangible/survey_tool/shared_survey_tool_gas.iff"       : 
				toolType = 4; 
				surveyEffectString = "clienteffect/survey_tool_gas.cef";
				sampleEffectString = "clienteffect/survey_sample_gas.cef";				
				break; 
			case "object/tangible/survey_tool/shared_survey_tool_moisture.iff"    : 
				toolType = 5; 
				surveyEffectString = "clienteffect/survey_tool_liquid.cef";
				sampleEffectString = "clienteffect/survey_sample_liquid.cef";
				break; 
			case "object/tangible/survey_tool/shared_survey_tool_wind.iff"      : 
				toolType = 6; 
				surveyEffectString = "clienteffect/survey_tool_gas.cef";
				sampleEffectString = "clienteffect/survey_sample_gas.cef";				
				break; 
			case "object/tangible/survey_tool/shared_survey_tool_solar.iff"     : 
				toolType = 7; 
				surveyEffectString = "clienteffect/survey_tool_moisture.cef";
				sampleEffectString = "clienteffect/survey_sample_moisture.cef";				
				break; 
			case "object/tangible/survey_tool/shared_survey_tool_all.iff"       : 
				toolType = 8; 
				surveyEffectString = "clienteffect/survey_tool_liquid.cef";
				sampleEffectString = "clienteffect/survey_sample_liquid.cef";
				break; 
			default: toolType = -1;
		}
		
	}
	
	public void setToolType(byte toolType) {
		this.toolType = toolType;
	}
	
	public byte getToolType() {
		return this.toolType;
	}
	
	public String getSurveyEffectString() {
		return surveyEffectString;
	}

	public String getSampleEffectString() {
		return sampleEffectString;
	}
	
	public void setCurrentlySurveying(boolean toggle){
		this.currentlySurveying=toggle;
	}
	
	public boolean getCurrentlySurveying(){
		return this.currentlySurveying;
	}
	
	public void setCurrentlySampling(boolean toggle){
		this.currentlySampling=toggle;
	}
	
	public boolean getCurrentlySampling(){
		return this.currentlySampling;
	}
	
	public void setLastSurveyTime(Long time){
		this.lastSurveyTime = time;
	}
	
	public Long getLastSurveyTime(){
		return this.lastSurveyTime;
	}
	
	public void setLastSampleTime(Long time){
		this.lastSampleTime = time;
	}
	
	public Long getLastSampleTime(){
		return this.lastSampleTime;
	}
	
	public void setSurveyResource(GalacticResource surveyResource){
		this.surveyResource = surveyResource;
	}
	
	public GalacticResource getSurveyResource(){
		return this.surveyResource;
	}

	public boolean getCurrentlyCoolingDown() {
		return currentlyCoolingDown;
	}

	public void setCurrentlyCoolingDown(boolean currentlyCoolingDown) {
		this.currentlyCoolingDown = currentlyCoolingDown;
	}

	public Long getRecoveryTime() {
		return recoveryTime;
	}

	public void setRecoveryTime(Long recoveryTime) {
		this.recoveryTime = recoveryTime;
	}

	public CreatureObject getUser() {
		return user;
	}

	public void setUser(CreatureObject user) {
		this.user = user;
	}

	public Long getTanoID() {
		return tanoID;
	}

	public void setTanoID(Long tanoID) {
		this.tanoID = tanoID;
	}

	public boolean isExceptionalState() {
		return exceptionalState;
	}

	public void setExceptionalState(boolean exceptionalState) {
		this.exceptionalState = exceptionalState;
	}

	public boolean isRecoveryMode() {
		return this.recoveryMode;
	}

	public void setRecoveryMode(boolean recoveryMode) {
		this.recoveryMode = recoveryMode;
	}

	public byte getSurveyRangeSetting() {
		return SurveyRangeSetting;
	}

	public void setSurveyRangeSetting(byte surveyRangeSetting) {
		SurveyRangeSetting = surveyRangeSetting;
	}
	
	public void sendConstructSurveyMapMessage(){
		float surveyRadius = 64.0f;		
		int surveyToolRangeSetting = this.getSurveyRangeSetting();
		//surveyToolRangeSetting = 4;
		int divisor = 0;
		if (surveyToolRangeSetting==0) {
			divisor = 2;
			surveyRadius = 64.0f;
		} else if (surveyToolRangeSetting==1) {
			divisor = 3;
			surveyRadius = 128.0f;
		} else if (surveyToolRangeSetting==2) {
			divisor = 3;
			surveyRadius = 192.0f;
		} else if (surveyToolRangeSetting==3) {
			divisor = 4;
			surveyRadius = 256.0f;
		} else if (surveyToolRangeSetting==4) {
			divisor = 4;
			surveyRadius = 320.0f;
		} else {
			divisor = 5;
			surveyRadius = 3072.0f;
		}

		float differential = surveyRadius / (float) divisor;
		GalacticResource resourceToSurvey = this.getSurveyResource();			
		Vector<ResourceConcentration> concentrationMap = resourceToSurvey.buildConcentrationsCollection(this.getUser().getPosition(),resourceToSurvey, surveyRadius, differential, this.getUser().getPlanetId());		
		this.getSurveyResource().constructSurveyMapMessage(this.getUser(), concentrationMap, surveyRadius);
		//this.getUser().sendSystemMessage("Distance to nearest Deposit : " + this.getSurveyResource().getHelperMinDist(), (byte) 0);
	}
}