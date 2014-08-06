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
package services.spawn;

public class CreatureSkill
{
	public String name;
	public long cooldown;
	public long usage;
	public Integer mask;
	public CreatureSkill(String name, long cooldown, Integer mask)
	{
		this.name=name;
		this.cooldown=cooldown;
		this.mask=mask;
	}
	public String getName()
	{
		return name;
	}
	public long getCooldown()
	{
		return cooldown;
	}
	public Integer getMask()
	{
		return mask;
	}
	public void setUsage(long usage)
	{
		this.usage=usage;
	}
	public long getUsage()
	{
		return usage;
	}
	public static final byte BUFF_SKILL=1;
	public static final byte DAMAGE_SKILL=2;
	public static final byte DEFENSE_SKILL=4;
	public static final byte DRAIN_SKILL=8;
	public static final byte HEAL_SKILL=16;

}



