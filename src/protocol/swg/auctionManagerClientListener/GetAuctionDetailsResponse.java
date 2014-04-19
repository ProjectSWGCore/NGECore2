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

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.SWGObject;
import protocol.swg.SWGMessage;
import resources.objects.tangible.TangibleObject;
import services.bazaar.AuctionItem;

public class GetAuctionDetailsResponse extends SWGMessage {

	private AuctionItem target;

	public GetAuctionDetailsResponse(AuctionItem target) {
		this.target = target;
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub

	}

	@Override
	public IoBuffer serialize() {
		final IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putShort((short) 2);
		result.putInt(0xFE0E644B);
		result.putLong(target.getObjectId());
		result.put(getUnicodeString(target.getItemDescription()));
		result.putInt(target.getItem().getAttributes().size());
		target.getItem().getAttributes().forEach((key, value) -> {
			result.put(getAsciiString(key));
			result.put(getUnicodeString(value));
		});
		result.put(getAsciiString(target.getItem().getTemplate()));
		if(((TangibleObject) target.getItem()).getCustomization() != null) {
			result.putShort((short) ((TangibleObject) target.getItem()).getCustomization().length);
			result.put(((TangibleObject) target.getItem()).getCustomization());
		} else
			result.putShort((short) 0);
			
		
		int size = result.position();
		IoBuffer result2 = IoBuffer.allocate(size).put(result.array(), 0, size);

		return result2.flip();
	}

}
