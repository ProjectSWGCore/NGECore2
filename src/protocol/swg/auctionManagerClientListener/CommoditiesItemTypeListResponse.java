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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;

import protocol.swg.SWGMessage;
import services.bazaar.AuctionItem;

public class CommoditiesItemTypeListResponse extends SWGMessage {
	
	private Map<Integer, List<AuctionItem>> itemList = new HashMap<Integer, List<AuctionItem>>();
	private static SimpleBufferAllocator bufferPool = new SimpleBufferAllocator();
	
	public void addItem(AuctionItem item) {
		if(itemList.get(item.getItemType()) == null)
			itemList.put(item.getItemType(), new ArrayList<AuctionItem>());
		itemList.get(item.getItemType()).add(item);
	}

	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Struct of category lists:
	 * int numberofcategories
	 * 
	 * int category
	 * number of items in category
	 * 
	 * int server crc (w/o shared_)
	 * int category
	 * astring stf filename
	 * int spacer
	 * astring stf name
	 * 
	 */

	@Override
	public IoBuffer serialize() {
		String galaxy = NGECore.getInstance().getGalaxyName();
		IoBuffer result = bufferPool.allocate(14 + galaxy.length(), false).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		result.putShort((short) 2);
		result.putInt(0xD4E937FC);
		result.put(getAsciiString(galaxy + "." + Integer.toString(itemList.size())));
		result.putInt(itemList.size());
		
		for(Entry<Integer, List<AuctionItem>> e : itemList.entrySet()) {
			result.putInt(e.getKey());
			result.putInt(e.getValue().size());
			for(AuctionItem item : e.getValue()) {
				result.putInt(item.getItemTypeCRC());
				result.putInt(item.getItemType());
				result.put(item.getItem().getObjectName().getBytes());
			}
		}
		
		int size = result.position();
		IoBuffer result2 = IoBuffer.allocate(size).put(result.array(), 0, size);

		return result2.flip();
	}

}
