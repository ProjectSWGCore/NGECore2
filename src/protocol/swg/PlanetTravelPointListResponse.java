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
package protocol.swg;

import java.nio.ByteOrder;
import java.util.Vector;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import services.travel.TravelPoint;
import engine.resources.scene.Planet;

@SuppressWarnings("unused")
public class PlanetTravelPointListResponse extends SWGMessage {

	private NGECore core;
	private Planet originatePlanet;
	private Vector<TravelPoint> travelPoints;
	
	public PlanetTravelPointListResponse(Planet planet, Vector<TravelPoint> travelPoints) {
		this.originatePlanet = planet;
	}
	
	@Override
	public void deserialize(IoBuffer data) {

	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putShort((short) 9);
		result.putInt(0x4D32541F);
		
		result.put(getAsciiString(originatePlanet.getName()));
		result.putInt(core.terrainService.getPlanetList().size());
		
		result.putInt(travelPoints.size()); // shuttles/sp count
		for (TravelPoint point : travelPoints) {
			result.put(getAsciiString(point.getName()));
		}
		
		result.putInt(travelPoints.size()); // shuttles/sp count
		for (TravelPoint point : travelPoints) {
			
			result.putFloat(point.getPosX());
			result.putFloat(point.getPosZ()); // Z and Y may be switched
			result.putFloat(point.getPosY());

		}
		
		result.putInt(travelPoints.size()); // shuttes/sp count
		for (TravelPoint point : travelPoints) {
			// TODO: Change ticket price based on planet/distance
			result.putInt(point.getTicketPrice());
		}
		
		result.putInt(travelPoints.size());
		for (TravelPoint point : travelPoints) {
			// TODO: Change based on planet/distance
			result.put((byte) 1); // isReachable
		}
		result.flip();
		return result;
	}

}
