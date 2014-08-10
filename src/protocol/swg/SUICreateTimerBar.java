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
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

@SuppressWarnings("unused")
public class SUICreateTimerBar extends SWGMessage{
	
	private String script;
	private int windowId;
	private int startCount;
	private int digitCount;
	private List<Integer> digits = new ArrayList<Integer>();
	String prompt;
		
	public SUICreateTimerBar(String script, int windowId, String prompt, int startCount) {
		
		this.script = script;
		this.windowId = windowId;
		this.startCount = startCount;
		this.prompt = prompt;
		
		digitCount = 3;
		if (startCount>9)
			digitCount = 4;
			
		while (startCount > 0) {
		  int d = startCount / 10;
		  int k = startCount - d * 10;
		  startCount = d;
		  digits.add((int)(Integer.toString(k).charAt(0)));
		  //System.out.println("digit: "+Integer.toHexString((int)(Integer.toString(k).charAt(0))));
		}
//		digits.add(0x39);
//		digits.add(0x31);
	}
		
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		
		IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putShort((short) 2);
		result.putInt(0xD44B7259);
		
		result.putInt(windowId);
		result.put(getAsciiString(script));
		result.putInt(5);
		result.putInt(5);
		result.put((byte)0);
		result.putInt(3);
		result.putShort((short)0);
		result.putShort((short)1);
		result.put((byte)9);
		
		result.put(getAsciiString("modifySlot"));
		result.putInt(5); 
		result.put((byte)0);
		result.putInt(3);
		result.putShort((short)0);
		result.putShort((short)1);
		result.put((byte)0xA);
		result.put(getAsciiString("modifySlot"));
		result.put((byte)3);
		result.putInt(1);
		
		result.put(getUnicodeString(prompt));
		
		result.putInt(2);
		
		result.put(getAsciiString("comp.text"));
		result.put(getAsciiString("Text"));
		
		result.put((byte)3);
		result.putInt(1);
		
		result.put(getUnicodeString("quest_countdown_timer"));
			
		result.putInt(2);
		
		result.put(getAsciiString("bg.caption.lblTitle"));
		result.put(getAsciiString("Text"));
		
			
		result.put((byte)3);
		result.putInt(1);
		result.putInt(digitCount);
		
		result.putShort((short)0x30);
		result.putShort((short)0x2C);
		for (int i=digits.size()-1;i>=0;i--)
			result.putShort((short)((int)digits.get(i)));
		
		result.putInt(2);
		result.put(getAsciiString("this"));
		result.put(getAsciiString("countdownTimerTimeValue"));
		
		result.putLong(0);
		result.putInt(0x7F7FFFFF);
		result.putInt(0x7F7FFFFF);
		result.putInt(0x7F7FFFFF);
		result.putInt(0);

		int size = result.position();
		result = IoBuffer.allocate(size).put(result.array(), 0, size);

		return result.flip();	
	}
}
