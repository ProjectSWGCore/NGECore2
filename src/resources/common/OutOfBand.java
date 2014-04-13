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
import resources.objects.Delta;

import org.apache.mina.core.buffer.IoBuffer;

public class OutOfBand {
	
	private Vector<ProsePackage> prosePackages = new Vector<ProsePackage>();
	private int headerBytes = 0;
	
	public OutOfBand(Vector<ProsePackage> prosePackages) {
		setProsePackages(prosePackages);
	}
	
	public OutOfBand(ProsePackage prosePackage) {
		addProsePackage(prosePackage);
	}
	
	public OutOfBand() {
		
	}
	
	public static OutOfBand ProsePackage(Object ... objects) {
		return new OutOfBand(new ProsePackage(objects));
	}
	
	public void addProsePackage(ProsePackage prosePackage) {
		prosePackages.add(prosePackage);
	}
	
	public IoBuffer serialize() {
		IoBuffer buffer = IoBuffer.allocate(50).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		
		// The short that was here is only in conversation OutOfBands.
		// Count doesn't seem to be count.  It seems to be indicating if there's an extra byte.
		
		for (ProsePackage prosePackage : prosePackages) {
			buffer.put((byte) 1);
			buffer.putInt(-1);
			buffer.put(prosePackage.getStf().getBytes());
			
			buffer.putLong(prosePackage.getTuObjectId());
			buffer.put(prosePackage.getTuStf().getBytes());
			buffer.put(StringUtilities.getUnicodeString(prosePackage.getTuCustomString()));
			
			buffer.putLong(prosePackage.getTtObjectId());
			buffer.put(prosePackage.getTtStf().getBytes());
			buffer.put(StringUtilities.getUnicodeString(prosePackage.getTtCustomString()));
			
			buffer.putLong(prosePackage.getToObjectId());
			buffer.put(prosePackage.getToStf().getBytes());
			buffer.put(StringUtilities.getUnicodeString(prosePackage.getToCustomString()));
			
			buffer.putInt(prosePackage.getDiInteger());
			buffer.putFloat(prosePackage.getDfFloat());
			
			buffer.put((byte) 0);
		}
		
		int size = buffer.position();
		
		if (size > 0) {
			if ((size % 2) == 1) {
				size++;
				buffer.put((byte) 0);
				buffer = Delta.createBuffer(size + 2).putShort((short) 1).put(buffer.flip().array(), 0, size);
			} else {
				buffer = Delta.createBuffer(size + 2).putShort((short) 0).put(buffer.flip().array(), 0, size);
			}
			
			size += 2;
		} else {
			return Delta.createBuffer(4).putInt(size);
		}
		
		if (headerBytes > 0) {
			IoBuffer result = Delta.createBuffer(size + headerBytes);
			
			for (int i = 0; i < headerBytes; i++) {
				result.put((byte) 0);
			}
			
			buffer = result.put(buffer.flip().array(), 0, size);
			size += headerBytes;
		}
		
		return Delta.resizeBuffer(Delta.createBuffer(size + 4).putInt(size / 2).put(buffer.flip().array()));
	}
	
	/*
	 * No idea what the extra short in conversation OutOfBands is.
	 * It's not in any of the other packets that use OutOfBand.
	 */
	public void setHeaderBytes(int headerBytes) {
		this.headerBytes = headerBytes;
	}
	
	public Vector<ProsePackage> getProsePackages() {
		return prosePackages;
	}
	
	public void setProsePackages(Vector<ProsePackage> prosePackages) {
		this.prosePackages = prosePackages;
	}
	
}
