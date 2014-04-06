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
package services.equipment;

import java.util.Vector;

import main.NGECore;

import org.python.core.Py;
import org.python.core.PyObject;

import engine.resources.objects.SWGObject;
import resources.objects.creature.CreatureObject;

public class BonusSetTemplate
{
	private String name;
	private Vector<String> requiredWornItems;
	
	public BonusSetTemplate(String name) 
	{
		this.name = name;
		this.requiredWornItems = new Vector<String>();
	}
	
	public void addRequiredItem(String item)
	{
		requiredWornItems.add(item);
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getWornTemplateCount(CreatureObject creature)
	{
		int wornItems = 0;
		for (SWGObject item : creature.getEquipmentList().get())
		{
			if(requiredWornItems.contains(item.getTemplate()) || requiredWornItems.contains(item.getStfName())) wornItems++;
		}
		return wornItems;
	}
	
	public void callScript(CreatureObject creature)
	{	
		PyObject func = NGECore.getInstance().scriptService.getMethod("scripts/equipment/bonus_sets/", name, "handleChange");
		if(func != null) func.__call__(Py.java2py(NGECore.getInstance()), Py.java2py(creature), Py.java2py(this));
	}
}
