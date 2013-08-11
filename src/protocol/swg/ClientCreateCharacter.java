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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;

public class ClientCreateCharacter extends SWGMessage {
	
	private String raceTemplate;
	private String hairObject;
	private byte[] hairCustomization;
	private byte[] customizationData;
	private float  scale;
	private String profession;
	private String professionWheelPosition;
	private String starterProfession;
	private String startingLocation;
	private String firstName;
	private String lastName;
	private String name;
	private boolean tutorial;
	
	public ClientCreateCharacter() {
		
	}
	
	public byte [] getCustomizationData()       { return customizationData; }
	public String  getFirstName()               { return firstName; }
	public String  getHairObject()              { return hairObject; }
	public byte [] getHairCustomization()       { return hairCustomization; }
	public String  getLastName()                { return lastName; }
	public String  getName()                    { return name; }
	public String  getRaceTemplate()            { return raceTemplate; }
	public String  getProfession()              { return profession; }
	public String  getProfessionWheelPosition() { return professionWheelPosition; }
	public float   getScale()                   { return scale; }
	public String  getStartingLocation()        { return startingLocation; }
	public String  getStarterProfession()       { return starterProfession; }
	public boolean wantsTutorial()              { return tutorial; }
	
	public void deserialize(IoBuffer buffer) {
		try {
			buffer.position(6); // Skips the SOE and SWG opcodes
			short length = buffer.getShort();
			customizationData = new byte[length];
			buffer.get(customizationData);
			int size = buffer.getInt();
			name = new String(ByteBuffer.allocate(size * 2).put(buffer.array(), buffer.position(), size * 2).array(), "UTF-16LE");
			buffer.position(buffer.position() + size * 2);
			size = buffer.getShort();
			raceTemplate = new String(ByteBuffer.allocate(size).put(buffer.array(), buffer.position(), size).array(), "US-ASCII");
			buffer.position(buffer.position() + size);
			size = buffer.getShort();
			startingLocation = new String(ByteBuffer.allocate(size).put(buffer.array(), buffer.position(), size).array(), "US-ASCII");
			buffer.position(buffer.position() + size);
			size = buffer.getShort();
			hairObject = new String(ByteBuffer.allocate(size).put(buffer.array(), buffer.position(), size).array(), "US-ASCII");
			buffer.position(buffer.position() + size);
			length = buffer.getShort();
			hairCustomization = new byte[length];
			buffer.get(hairCustomization);
			size = buffer.getShort();
			starterProfession = new String(ByteBuffer.allocate(size).put(buffer.array(), buffer.position(), size).array(), "US-ASCII"); // no
			buffer.position(buffer.position() + size);
			buffer.get();                    // unk this seems to always be 0x00
			scale = buffer.getFloat();	     // height?
			buffer.getInt();                 // Biography. There is no option to create a biography in NGE, so this is unnecessary
			tutorial = buffer.get() == 0x01; // Tutorial Byte Flag, 0x01 if tutorial is selected
			size = buffer.getShort();
			profession = new String(ByteBuffer.allocate(size).put(buffer.array(), buffer.position(), size).array(), "US-ASCII"); // NGE
			buffer.position(buffer.position() + size);
			size = buffer.getShort();
			professionWheelPosition = new String(ByteBuffer.allocate(size).put(buffer.array(), buffer.position(), size).array(), "US-ASCII"); // this
			System.out.println(name + " " + raceTemplate);
			// Gets the first and last names
			String [] splitName = name.split(" ");
			firstName = splitName[0];
			lastName  = (splitName.length == 1) ? "" : splitName[1];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public IoBuffer serialize() {
		return IoBuffer.allocate(0);
	}

}
