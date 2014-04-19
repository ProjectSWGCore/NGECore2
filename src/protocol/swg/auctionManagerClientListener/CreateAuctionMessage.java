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
package protocol.swg.auctionManagerClientListener;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.common.Utilities;
import protocol.swg.SWGMessage;

public class CreateAuctionMessage extends SWGMessage {

	private long objectId;
	private long vendorId;
	private int price;
	private int duration;
	private String description;
	private byte premium;

	@Override
	public void deserialize(IoBuffer data) {
		data.skip(6);
		setObjectId(data.getLong());		
		int size = data.getInt();
		try {
			setDescription(new String(ByteBuffer.allocate(size * 2).put(data.array(), data.position(), size * 2).array(), "UTF-16LE"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		data.position(data.position() + size * 2);

		setVendorId(data.getLong());
		setPrice(data.getInt());
		setDuration(data.getInt()); // in minutes
		setPremium(data.get());
	}

	@Override
	public IoBuffer serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public long getVendorId() {
		return vendorId;
	}

	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getPremium() {
		if(premium == 1)
			return true;
		return false;
	}

	public void setPremium(byte premium) {
		this.premium = premium;
	}

}
