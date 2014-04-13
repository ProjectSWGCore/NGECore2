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
package resources.objects.deed;
import com.sleepycat.persist.model.Persistent;

import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

/** 
 * @author Seefo 
 * @author Charon 
 */

@Persistent(version=0)
public class Player_House_Deed extends Deed {
	
	private String name;
	private String structureTemplate;
	private String constructorTemplate;
	private int BMR=0;
	private int surplusMaintenance=0;
	private int lotRequirement;
	
	
	public Player_House_Deed(long objectID, Planet planet, String template, Point3D position, Quaternion orientation){
		super(objectID, planet, template, position, orientation);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStructureTemplate() {
		return structureTemplate;
	}
	public void setStructureTemplate(String structureTemplate) {
		this.structureTemplate = structureTemplate;
	}
	public int getLotRequirement() {
		return lotRequirement;
	}
	public void setLotRequirement(int lotRequirement) {
		this.lotRequirement = lotRequirement;
	}
	public String getConstructorTemplate() {
		return constructorTemplate;
	}
	public void setConstructorTemplate(String constructorTemplate) {
		this.constructorTemplate = constructorTemplate;
	}
	public int getBMR() {
		return BMR;
	}
	public void setBMR(int BMR) {
		this.BMR = BMR;
	}
	public int getSurplusMaintenance() {
		return surplusMaintenance;
	}
	public void setSurplusMaintenance(int surplusMaintenance) {
		this.surplusMaintenance = surplusMaintenance;
	}
	
	public void setAttributes() {
		this.getAttributes().put("@obj_attr_n:volume", "1");
		this.getAttributes().put("@obj_attr_n:examine_maintenance_rate", ""+this.getBMR() + "/hour");
		if (this.getSurplusMaintenance()>0)
			this.getAttributes().put("@obj_attr_n:examine_maintenance", ""+this.getSurplusMaintenance());	
			

	}
}
