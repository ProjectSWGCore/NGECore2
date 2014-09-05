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

@Deprecated
public class SUICreateTimerBar extends SWGMessage{
	
	private String script;
	private int windowId;
	private int startCount;
	private int digitCount;
	private List<Integer> digits = new ArrayList<Integer>();
	private String prompt;
		
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
	
	/*
	This is just a simple SUICreatePageMessage and can be achieved through the proper system:
	
	02 00 F6 42 33 5F 61 EF 00 00 18 00 53 63 72 69    ...B3_a.....Scri
	70 74 2E 43 6F 75 6E 74 64 6F 77 6E 54 69 6D 65    pt.CountdownTime
	72 42 61 72 03 00 00 00 03 01 00 00 00 28 00 00    rBar.........(..
	00 40 00 73 00 61 00 67 00 61 00 5F 00 73 00 79    .@.s.a.g.a._.s.y
	00 73 00 74 00 65 00 6D 00 3A 00 68 00 6F 00 6C    .s.t.e.m.:.h.o.l
	00 6F 00 63 00 72 00 6F 00 6E 00 5F 00 63 00 72    .o.c.r.o.n._.c.r
	00 65 00 61 00 74 00 69 00 6F 00 6E 00 5F 00 63    .e.a.t.i.o.n._.c
	00 6F 00 75 00 6E 00 74 00 64 00 6F 00 77 00 6E    .o.u.n.t.d.o.w.n
	00 02 00 00 00 09 00 63 6F 6D 70 2E 74 65 78 74    .......comp.text
	04 00 54 65 78 74 03 01 00 00 00 15 00 00 00 70    ..Text.........p
	00 67 00 63 00 5F 00 68 00 6F 00 6C 00 6F 00 63    .g.c._.h.o.l.o.c
	00 72 00 6F 00 6E 00 5F 00 63 00 72 00 65 00 61    .r.o.n._.c.r.e.a
	00 74 00 69 00 6F 00 6E 00 02 00 00 00 13 00 62    .t.i.o.n.......b
	67 2E 63 61 70 74 69 6F 6E 2E 6C 62 6C 54 69 74    g.caption.lblTit
	6C 65 04 00 54 65 78 74 03 01 00 00 00 03 00 00    le..Text........
	00 30 00 2C 00 33 00 02 00 00 00 04 00 74 68 69    .0.,.3.......thi
	73 17 00 63 6F 75 6E 74 64 6F 77 6E 54 69 6D 65    s..countdownTime
	72 54 69 6D 65 56 61 6C 75 65 00 00 00 00 00 00    rTimeValue......
	00 00 E2 CE 48 C5 5D BF C7 40 A9 33 FC 44 00 00    ....H.]..@.3.D..
	40 40                                              @@
	*/
	
}
