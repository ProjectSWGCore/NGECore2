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

import java.math.BigDecimal;
import java.util.Random;

import engine.resources.scene.*;

public class MathUtilities {
	
	public static Quaternion rotateQuaternion(Quaternion quaternion, float radians, Point3D axis) {
		
		float sin = (float) Math.sin(radians / 2);
	
		Quaternion rotateQ = new Quaternion((float) Math.cos(radians / 2), axis.x * sin, axis.y * sin, axis.z * sin);
		
		return multiplyQuaternions(rotateQ, quaternion);
		
	}
	
	public static Quaternion multiplyQuaternions(Quaternion q1, Quaternion q2) {
		
		return new Quaternion(q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z,
				q1.w * q2.x + q1.x * q2.w + q1.y * q2.z - q1.z * q2.y,
				q1.w * q2.y + q1.y * q2.w + q1.z * q2.x - q1.x * q2.z,
				q1.w * q2.z + q1.z * q2.w + q1.x * q2.y - q1.y * q2.x);
		
	}
	
	/**
	 * Converts seconds to a whole hour.
	 * @author Waverunner
	 * @param seconds
	 * @return hour(s)
	 */
	public static int secondsToWholeHours(int seconds) {
		BigDecimal dec = new BigDecimal(seconds);

		return dec.divide(new BigDecimal(3600), BigDecimal.ROUND_FLOOR).intValue();
	}
	
	/**
	 * Converts seconds to minutes of the hour.
	 * @author Waverunner
	 * @param seconds
	 * @return hour(s)
	 */
	public static int secondsToHourMinutes(int seconds) {
		BigDecimal dec = new BigDecimal(seconds);
		return dec.remainder(new BigDecimal(3600)).divide(new BigDecimal(60), BigDecimal.ROUND_FLOOR).intValue();
	}
	
	public float fastInverseSqrt(float x) {
	    float xHalf = 0.5F * x;
	    int temp = Float.floatToRawIntBits(x);
	    temp = 0x5F3759DF - (temp >> 1); // magic
	    float newX = Float.intBitsToFloat(temp);
	    newX = newX * (1.5F - xHalf * newX * newX);
	    return newX;
	}
	
	public static boolean tryChance(int chance) {
		if (chance <= 0) {
			return false;
		}
		
		if (chance >= 100) {
			return true;
		}
		
		chance = (100 - chance);
		
		Random random = new Random();
		
		if (random.nextInt(chance + 1) == 1) {
			return true;
		}
		
		return false;
	}
	
}
