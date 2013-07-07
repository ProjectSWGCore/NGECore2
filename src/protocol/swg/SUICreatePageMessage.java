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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;


public class SUICreatePageMessage extends SWGMessage{
	
	private String script;
	private byte[] header;
	private int optionCount;
	private int windowId;
	private byte[] body;
	private long objectId;
	
	
	
	public SUICreatePageMessage() {
		
		ByteBuffer result = ByteBuffer.allocate(6).order(ByteOrder.LITTLE_ENDIAN);
		
		
		
		
		
		/*
		result.putShort((short) 2);
		result.putInt(0xD44B7259);  // CRC
		result.putInt(0x00F85E88); //I'm gonna guess this is an ID of sorts.
		result.put(getAsciiString("Script.transfer"));		// change this to Script.InputBox and look at the things you can select lol
		
		result.putInt(8);		//list size/number of lists
		
		//---------------------1
		
		
	
	    result.put((byte)5);
	    result.putInt(0); //counter I think.
	 
	
	    result.putInt(3); //list size
	    result.putShort((short) 0);
	    result.putShort((short) 1);		// unks
	    result.put((byte) 9);
	    result.put(getAsciiString("handleSUI"));
	    
		//---------------------2
		
		
		
	    result.put((byte)5);
	    result.putInt(0); //counter I think.
	 
	
	    result.putInt(3); //list size
	    result.putShort((short) 0);
	    result.putShort((short) 1);		// unks
	    result.put((byte) 0x0A);
	    result.put(getAsciiString("handleSUI"));
	    
	
	    //--------------------------- 3
	    result.put((byte)3);
	    result.putInt(1); //counter I think
	    result.put(getUnicodeString("@travel:ticket_purchase_complete"));
	
	    result.putInt(2); //counter I think
	    result.put(getAsciiString("Prompt.lblPrompt"));
	    result.put(getAsciiString("Text"));
	    
	    
	
	
	    //--------------------------- 4
	    result.put((byte)3);
	    result.putInt(1); //counter I think
	   	result.put(getUnicodeString("@base_player:swg"));
	
		result.putInt(2); //counter I think
		result.put(getAsciiString("bg.caption.lblTitle"));
		result.put(getAsciiString("Text"));
	
		
		
	   	//--------------------------- 5
	  	result.put((byte)3);
	 	result.putInt(1); //counter I think
		result.put(getUnicodeString("True"));
	
		result.putInt(2); //counter I think
		result.put(getAsciiString("btnCancel"));
	  	result.put(getAsciiString("Enabled"));
	
	  	//--------------------------- 6
	  	result.put((byte)3);
	  	result.putInt(1); //counter I think
	  	result.put(getUnicodeString("False"));
	
	  	result.putInt(2); //counter I think
	  	result.put(getAsciiString("btnCancel"));
	  	result.put(getAsciiString("Visible"));
	
	
	
	
	  	//--------------------------- 7
	  	result.put((byte)4);
	  	result.putInt(1);
	  	result.put(getUnicodeString("False"));
	  	result.putInt(2); //counter I think
	  	result.put(getAsciiString("btnRevert"));
	  	result.put(getAsciiString("Enabled"));
	
	    //--------------------------- 8
	  	result.put((byte)3);
	  	result.putInt(1);
	    result.put(getUnicodeString("False"));
	    result.putInt(2); //counter I think
	    result.put(getAsciiString("btnRevert"));
	    result.put(getAsciiString("Visible"));
	

        //I dunno what these are/do.
        result.putLong(0);

        result.putInt(0x7F7FFFFF);
        result.putInt(0x7F7FFFFF);
        result.putInt(0x7F7FFFFF); 
        result.putInt(0);
		 */
		
		
		result.putShort((short) 2);
		result.putInt(0xD44B7259);  // CRC
		
		
		
		data = result.array();
		
		
		
	}
	
	public void addHeader(String handler, String[] options, int windowId, String script, long objectId) {
		
		
		this.windowId = windowId;
		this.script = script;
		this.objectId = objectId;
		
		if(options != null) {
			ByteBuffer result = ByteBuffer.allocate((18 + handler.length() + options.length) * 2).order(ByteOrder.LITTLE_ENDIAN);
			result.put((byte) 5);
			result.putInt(0);
			int counter = 0;
			for (int i = 0; i < options.length; i ++){
				if (options[i] != null) {
					counter ++;
				}
				
			}    
			result.putInt(counter+3);
			result.putShort((short) 0);
			result.putShort((short) 1);
			result.put((byte) 0x09);
			result.put(getAsciiString(handler));
			for (int i = 0; i < options.length; i ++){
				if (options[i] != null) {
					result.put(getAsciiString(options[i]));
				}
				
			}
			
			result.put((byte) 5);
			result.putInt(0);
			
			result.putInt(counter+3);
			result.putShort((short) 0);
			result.putShort((short) 1);
			result.put((byte) 0x0A);
			result.put(getAsciiString(handler));
			for (int i = 0; i < options.length; i ++){
				if (options[i] != null) {
					result.put(getAsciiString(options[i]));
				}
				
			}
			
			
			if (header == null)
				header = result.array();
			else
				header = ByteBuffer.allocate(header.length + result.capacity())
				.put(header)
				.put(result.array())
				.array();
			optionCount++;
		}
		
		else {
			ByteBuffer result = ByteBuffer.allocate((16 + handler.length()) * 2).order(ByteOrder.LITTLE_ENDIAN);
			result.put((byte) 5);
			result.putInt(0);
			
			result.putInt(3);
			result.putShort((short) 0);
			result.putShort((short) 1);
			result.put((byte) 0x09);
			result.put(getAsciiString(handler));
			
			
			result.put((byte) 5);
			result.putInt(0);
			
			result.putInt(3);
			result.putShort((short) 0);
			result.putShort((short) 1);
			result.put((byte) 0x0A);
			result.put(getAsciiString(handler));
			
			
			
			header = result.array();
			optionCount++;
			optionCount++;
			
		}
		
	}
	
	public void addBodyElement(byte type, String wideParameter, String narrowParameterVariable, String narrowParameterSetting) {
		
		
		
		if(type == 3 || type == 4) {
			ByteBuffer result = ByteBuffer.allocate(17 + wideParameter.length() * 2 + narrowParameterVariable.length() + narrowParameterSetting.length()).order(ByteOrder.LITTLE_ENDIAN);
			
			
			result.put(type);
			result.putInt(1);
			result.put(getUnicodeString(wideParameter));
			result.putInt(2);
			result.put(getAsciiString(narrowParameterVariable));
			result.put(getAsciiString(narrowParameterSetting));
			
			if (body == null)
				body = result.array();
			else
				body = ByteBuffer.allocate(body.length + result.capacity())
				.put(body)
				.put(result.array())
				.array();
			optionCount++;
			
			
		}
		
		else {
			ByteBuffer result = ByteBuffer.allocate(13 + wideParameter.length() * 2).order(ByteOrder.LITTLE_ENDIAN);
			
			result.put(type);
			result.putInt(0);
			result.putInt(1);
			result.put(getUnicodeString(wideParameter));
			
			if (body == null)
				body = result.array();
			else
				body = ByteBuffer.allocate(body.length + result.capacity())
				.put(body)
				.put(result.array())
				.array();
			optionCount++;
			
			
		}
		
	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		
		IoBuffer result = IoBuffer.allocate(34 + data.length + header.length + body.length + script.length()).order(ByteOrder.LITTLE_ENDIAN);
		result.put(data);
		result.putInt(windowId); 
		result.put(getAsciiString(script));	
		result.putInt(optionCount);		//list size/number of lists
		result.put(header);
		result.put(body);
		result.putLong(objectId);	
		result.putFloat(0);	//range to object specified by objectId, if 0 range is unlimited
		result.putLong(0);
		/*result.putInt(0x7F7FFFFF);
        result.putInt(0x7F7FFFFF);
        result.putInt(0x7F7FFFFF); 
        result.putInt(0);*/
		
		return result;	
	}
}
