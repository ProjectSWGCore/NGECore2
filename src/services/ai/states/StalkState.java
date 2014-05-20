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

import services.ai.AIActor;

public class StalkState extends AIState {

	@Override
	public byte onEnter(AIActor actor) throws Exception {
		actor.setStalking(true);
		return StateResult.UNFINISHED;
	}

	@Override
	public byte onExit(AIActor actor) throws Exception {
		actor.setStalking(false);
		return StateResult.UNFINISHED;
	}

	@Override
	public byte move(AIActor actor) throws Exception {
		if(!actor.isStalking() || actor.getFollowObject() == null)
			return StateResult.FINISHED;
		return StateResult.UNFINISHED;

	}

	@Override
	public byte recover(AIActor actor) throws Exception {
		if(!actor.isStalking() || actor.getFollowObject() == null)
			return StateResult.FINISHED;
		return StateResult.UNFINISHED;
	}

}
