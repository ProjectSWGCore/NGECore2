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

public class ObjControllerOpcodes {
	
	public static final int DATA_TRANSFORM = 0x71000000;
	public static final int DATA_TRANSFORM_WITH_PARENT = 0xF1000000;
	public static final int COMMAND_QUEUE_ENQUEUE = 0x16010000;
	public static final int COMMAND_QUEUE_REMOVE = 0x17010000;
	public static final int lookAtTarget = 0x26010000;
	public static final int intendedTarget = 0xC5040000;
	public static final int OBJECT_MENU_REQUEST = 0x46010000;
	public static final int SECURE_TRADE = 0x15010000;
	public static final int BUFF_BUILDER_CHANGE = 0x5A020000;
	public static final int BUFF_BUILDER_END = 0x5B020000;
	public static final int MISSION_LIST_REQUEST = 0xF5000000;
	public static final int ChangeRoleIconChoice = 0x4D040000;
	public static final int IMAGE_DESIGN_CHANGE = 0x38020000;
	public static final int IMAGE_DESIGN_END = 0x39020000;
	public static final int NPC_CONVERSATION_MESSAGE = 0xDF000000;
	public static final int START_NPC_CONVERSATION = 0xDD000000;
	public static final int STOP_NPC_CONVERSATION = 0xDE000000;
	public static final int NPC_CONVERSATION_OPTIONS = 0xE0000000;
	public static final int SET_PROFESSION_TEMPLATE = 0x5C040000;

}
