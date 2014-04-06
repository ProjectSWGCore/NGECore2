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

import java.nio.ByteOrder;
import java.util.Vector;
import resources.common.StringUtilities;
import org.apache.mina.core.buffer.IoBuffer;

public class OutOfBand {
	
	private short count;
	private Vector<ProsePackage> prosePackages = new Vector<ProsePackage>();
	
	public void addProsePackage(ProsePackage prosePackage) {
		prosePackages.add(prosePackage);
		setCount((short) (getCount() + 1));
	}
	
	public IoBuffer serialize() {
		
		IoBuffer buffer = IoBuffer.allocate(50).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		
		buffer.putInt(4);
		buffer.putShort((short) 0); // unk
		buffer.putShort(getCount());
		
		for(ProsePackage prosePackage : prosePackages) {
			
			buffer.put((byte) 1);
			buffer.putInt(-1);
			buffer.put(StringUtilities.getAsciiString(prosePackage.getStfFile()));
			buffer.putInt(0);
			buffer.put(StringUtilities.getAsciiString(prosePackage.getStfLabel()));
			
			buffer.putLong(prosePackage.getTuObjectId());
			buffer.put(StringUtilities.getAsciiString(prosePackage.getTuStfFile()));
			buffer.putInt(0);
			buffer.put(StringUtilities.getAsciiString(prosePackage.getTuStfLabel()));
			buffer.put(StringUtilities.getUnicodeString(prosePackage.getTuCustomString()));

			buffer.putLong(prosePackage.getTtObjectId());
			buffer.put(StringUtilities.getAsciiString(prosePackage.getTtStfFile()));
			buffer.putInt(0);
			buffer.put(StringUtilities.getAsciiString(prosePackage.getTtStfLabel()));
			buffer.put(StringUtilities.getUnicodeString(prosePackage.getTtCustomString()));
			
			buffer.putLong(prosePackage.getToObjectId());
			buffer.put(StringUtilities.getAsciiString(prosePackage.getToStfFile()));
			buffer.putInt(0);
			buffer.put(StringUtilities.getAsciiString(prosePackage.getToStfLabel()));
			buffer.put(StringUtilities.getUnicodeString(prosePackage.getToCustomString()));
			
			buffer.putInt(prosePackage.getDiInteger());
			buffer.putFloat(prosePackage.getDfFloat());
			
			//buffer.put(prosePackage.getDisplayFlag());
			buffer.put((byte) 0);

			int stfLength = prosePackage.getStfFile().length() + prosePackage.getStfLabel().length() +
							prosePackage.getTuStfFile().length() + prosePackage.getTuStfLabel().length() +
							prosePackage.getTtStfFile().length() + prosePackage.getTtStfLabel().length() +
							prosePackage.getToStfFile().length() + prosePackage.getToStfLabel().length();
			
			//if(stfLength % 2 == 1) {
				buffer.put((byte) 0);
			//}
			
		}
		
		setLength(buffer);
		int size = buffer.position();
		buffer.flip();
		return IoBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN).put(buffer.array(), 0, size).flip();
	}
	
	public void setLength(IoBuffer buffer) {
		buffer.putInt(0, (buffer.position() - 4) / 2);
	}
		
	public short getCount() {
		return count;
	}

	public void setCount(short count) {
		this.count = count;
	}

	public Vector<ProsePackage> getProsePackages() {
		return prosePackages;
	}

	public void setProsePackages(Vector<ProsePackage> prosePackages) {
		this.prosePackages = prosePackages;
	}
	
	

}
