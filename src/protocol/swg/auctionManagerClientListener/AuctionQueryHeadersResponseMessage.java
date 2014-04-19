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

import java.nio.ByteOrder;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.common.CRC;
import protocol.swg.SWGMessage;
import services.bazaar.AuctionItem;

public class AuctionQueryHeadersResponseMessage extends SWGMessage {
	
	private int screen;
	private int counter;
	private LinkedHashSet<String> locationList = new LinkedHashSet<String>();
	private LinkedHashSet<AuctionItem> itemList = new LinkedHashSet<AuctionItem>();
	private long playerId;
	private short offset;
	private boolean continues;

	public AuctionQueryHeadersResponseMessage(long playerId, int screen, int counter, short offset, boolean continues) {
		this.playerId = playerId;
		this.screen = screen;
		this.counter = counter;
		this.offset = offset;
		this.continues = continues;
	}

	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub

	}
	
	public void addItem(AuctionItem item) {
		locationList.add(item.getVuid());
		locationList.add(item.getOwnerName());
		
		if(item.isAuction() && item.getStatus() == AuctionItem.FORSALE)
			locationList.add(item.getBidderName());
		else
			locationList.add("");

		itemList.add(item);
	}

	@Override
	public IoBuffer serialize() {
		final IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putShort((short) 8);
		result.putInt(0xFA500E52);
		result.putInt(counter);
		result.putInt(screen);
		
		result.putInt(locationList.size());
		
		for(String str : locationList) {
			result.put(getAsciiString(str));
		}
		
		result.putInt(itemList.size());

		for(AuctionItem item : itemList) {
			result.put(getUnicodeString(item.getItemName()));
		}
		
		result.putInt(itemList.size());
		
		int i = 0;
		for(AuctionItem item : itemList) {
			
			result.putLong(item.getObjectId());
			result.put((byte) i);
			result.putInt(item.getPrice());
			result.putInt((int) ((item.getExpireTime() - System.currentTimeMillis()) / 1000));
			result.putInt(item.getPrice());
			result.putShort((short) 0);
			result.put((byte) 0);
			result.putInt(CRC.StringtoCRC(item.getItem().getTemplate()));
			result.putShort((short) 0);
			result.put((byte) 0);
			result.put((byte) 1);
			result.putInt(0);
			result.putInt(0);
			result.put((byte) 0);
			result.putInt(2);
			result.putInt(0);
			result.putShort((short) 0);
			result.putInt(item.getItemType());
			result.putInt(0);
			result.putInt(0);
			result.putInt(0);

/*
			result.put((byte) (item.isAuction() ? 0 : 1));
			result.putShort((short) getStringIndex(item.getVuid()));
			result.putLong(item.getOwnerId());
			result.putShort((short) getStringIndex(item.getOwnerName()));
			
			if(item.isAuction() && item.getStatus() == AuctionItem.FORSALE) {
				result.putLong(item.getBuyerId());
				result.putShort((short) getStringIndex(item.getBidderName()));
			} else {
				result.putLong(0);
				result.putShort((short) getStringIndex(""));
			}
			
			result.putInt(item.getProxyBid());
			result.putInt(item.getPrice());
			result.putInt(item.getItemType());

			int options = 0;
			
			if(item.getOwnerId() == playerId && (item.getStatus() == AuctionItem.OFFERED || item.getStatus() == AuctionItem.FORSALE)) 
				options |= 0x800;
			
			result.putInt(item.getAuctionOptions() | options);
			result.putInt(0);
			*/
			i++;

		}

		result.putShort(offset);
		
		if(continues)
			result.put((byte) 1);
		else
			result.put((byte) 0);

		int size = result.position();
		IoBuffer result2 = IoBuffer.allocate(size).put(result.array(), 0, size);

		return result2.flip();
	}
	
	private int getStringIndex(String str) {
		int i = 0;
		for(String string : locationList) {
			if(str.equals(string))
				return i;
			i++;
		}
		return i;
	}

	public void setContinues(boolean continues) {
		this.continues = continues;
	}

}
