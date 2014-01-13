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
package services.ai.states;

public abstract class AIState {

	public abstract byte onEnter();
	public abstract byte onExit();
	public abstract byte move();
	public abstract byte recover();
	
	public enum StateResult {;
	
		// default error state result
		public static final byte NONE = 0;
		// finished with state
		public static final byte FINISHED = 1;
		// unfinished with state, call recover()
		public static final byte UNFINISHED = 2;
		public static final byte DEAD = 3;
		// for deaggro or idling
		public static final byte IDLE = 4;
	
	}
	
	

}
