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
package protocol.swg.objectControllerObjects;

import java.nio.ByteOrder;
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.ObjControllerMessage;
import resources.common.IDAttribute;

public class ImageDesignMessage extends ObjControllerObject {

	private long designerId;
	private long targetId;
	private long objectId;
	private byte unkByte;
	private String hair;
	private String unk;
	private int sessionId;
	private int moneyDemanded;
	private int moneyOffered;
	private boolean designerCommited;
	private boolean customerAccepted;
	private byte flagStatMigration;
	
	private int bodyFormSkill1;
	private int faceFormSkill1;
	private int bodyFormSkill2;
	private int faceFormSkill2;
	
	private String holoEmote;
	
	private Vector<IDAttribute> bodyAttributes;
	private Vector<IDAttribute> colorAttributes;

	private boolean endMessage;
	
	public ImageDesignMessage() { }
	
	public ImageDesignMessage(long objectId, long designerId, long targetId) {
		this.objectId = objectId;
		this.designerId = designerId;
		this.targetId = targetId;
	}
	
	@Override
	public void deserialize(IoBuffer data) {

		setObjectId(data.getLong());
		data.getInt();
		setDesignerId(data.getLong());
		setTargetId(data.getLong());
		data.getLong(); // tent id
		
		setUnkByte(data.get());
		setHair(getAsciiString(data));
		
		setUnk(getAsciiString(data));
		data.getInt(); // timer
		setSessionId(data.getInt());
		setMoneyDemanded(data.getInt());
		setMoneyOffered(data.getInt());
		setDesignerCommited((boolean) ((data.get() == 1) ? true : false));
		setCustomerAccepted((boolean) ((data.getInt() == 1) ? true : false));
		
		setBodyFormSkill1(data.getInt());
		setFaceFormSkill1(data.getInt());
		
		setBodyFormSkill2(data.getInt());
		setFaceFormSkill2(data.getInt());
		
		int bodyAttributesSize = data.getInt();

		if (bodyAttributesSize > 0) {
			Vector<IDAttribute> bodyAttributes = new Vector<IDAttribute>();
			
			for (int i = 0; i < bodyAttributesSize; i++) {
				
				IDAttribute atr = new IDAttribute();
				
				atr.setName(getAsciiString(data));
				atr.setFloatValue(data.getFloat());
				
				bodyAttributes.add(atr);
			}
			setBodyAttributes(bodyAttributes);
		}
		
		int colorAttributesSize = data.getInt();

		if (colorAttributesSize > 0) {
			Vector<IDAttribute> colorAttributes = new Vector<IDAttribute>();
			
			for (int i = 0; i < colorAttributesSize; i++) {
				
				IDAttribute atr = new IDAttribute();

				atr.setName(getAsciiString(data));
				atr.setValue(data.getInt());

				colorAttributes.add(atr);
			}
			setColorAttributes(colorAttributes);
		}
		
		setHoloEmote(getAsciiString(data));
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer buffer = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		
		if (endMessage)
			buffer.putInt(ObjControllerMessage.IMAGE_DESIGN_END);
		else
			buffer.putInt(ObjControllerMessage.IMAGE_DESIGN_CHANGE);
		
		buffer.putLong(objectId);
		buffer.putInt(0);
		
		buffer.putLong(designerId);
		buffer.putLong(targetId);
		buffer.putLong(0); // tent id
		
		buffer.put(unkByte);
		if (hair != null)
			buffer.put(getAsciiString(hair));
		else
			buffer.putShort((short) 0);
		
		if (unk != null)
			buffer.put(getAsciiString(unk));
		else
			buffer.putShort((short) 0);
		
		buffer.putInt(0); // timer
		
		buffer.putInt(sessionId);
		
		buffer.putInt(moneyDemanded);
		buffer.putInt(moneyOffered);
		
		buffer.put((byte) ((designerCommited) ? 1 : 0));
		buffer.putInt((int) ((customerAccepted) ? 1 : 0));
		
		buffer.putInt(bodyFormSkill1);
		buffer.putInt(faceFormSkill1);
		buffer.putInt(bodyFormSkill2);
		buffer.putInt(faceFormSkill2);
		
		if(bodyAttributes != null && bodyAttributes.size() > 0) {
			buffer.putInt(bodyAttributes.size());
			for (IDAttribute atr : bodyAttributes) {
				buffer.put(getAsciiString(atr.getName()));
				buffer.putFloat(atr.getFloatValue());
			}
		} else {
			buffer.putInt(0);
		}
		
		if (colorAttributes != null && colorAttributes.size() > 0) {
			buffer.putInt(colorAttributes.size());
			for (IDAttribute atr : colorAttributes) {
				buffer.put(getAsciiString(atr.getName()));
				buffer.putInt(atr.getValue());
			}
		} else {
			buffer.putInt(0);
		}
		
		if (holoEmote != null)
			buffer.put(getAsciiString(holoEmote));
		else
			buffer.putShort((short) 0);
		
		return buffer.flip();
	}

	public long getDesignerId() {
		return designerId;
	}

	public void setDesignerId(long designerId) {
		this.designerId = designerId;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public byte getUnkByte() {
		return unkByte;
	}

	public void setUnkByte(byte unkByte) {
		this.unkByte = unkByte;
	}

	public String getHair() {
		return hair;
	}

	public void setHair(String hair) {
		this.hair = hair;
	}

	public String getUnk() {
		return unk;
	}

	public void setUnk(String unk) {
		this.unk = unk;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public int getMoneyDemanded() {
		return moneyDemanded;
	}

	public void setMoneyDemanded(int moneyDemanded) {
		this.moneyDemanded = moneyDemanded;
	}

	public int getMoneyOffered() {
		return moneyOffered;
	}

	public void setMoneyOffered(int moneyOffered) {
		this.moneyOffered = moneyOffered;
	}

	public boolean isDesignerCommited() {
		return designerCommited;
	}

	public void setDesignerCommited(boolean designerCommited) {
		this.designerCommited = designerCommited;
	}

	public boolean isCustomerAccepted() {
		return customerAccepted;
	}

	public void setCustomerAccepted(boolean customerAccepted) {
		this.customerAccepted = customerAccepted;
	}

	public byte getFlagStatMigration() {
		return flagStatMigration;
	}

	public void setFlagStatMigration(byte flagStatMigration) {
		this.flagStatMigration = flagStatMigration;
	}

	public int getBodyFormSkill1() {
		return bodyFormSkill1;
	}

	public void setBodyFormSkill1(int bodyFormSkill1) {
		this.bodyFormSkill1 = bodyFormSkill1;
	}

	public int getFaceFormSkill1() {
		return faceFormSkill1;
	}

	public void setFaceFormSkill1(int faceFormSkill1) {
		this.faceFormSkill1 = faceFormSkill1;
	}

	public int getBodyFormSkill2() {
		return bodyFormSkill2;
	}

	public void setBodyFormSkill2(int bodyFormSkill2) {
		this.bodyFormSkill2 = bodyFormSkill2;
	}

	public int getFaceFormSkill2() {
		return faceFormSkill2;
	}

	public void setFaceFormSkill2(int faceFormSkill2) {
		this.faceFormSkill2 = faceFormSkill2;
	}

	public String getHoloEmote() {
		return holoEmote;
	}

	public void setHoloEmote(String holoEmote) {
		this.holoEmote = holoEmote;
	}

	public Vector<IDAttribute> getBodyAttributes() {
		return bodyAttributes;
	}

	public void setBodyAttributes(Vector<IDAttribute> bodyAttributes) {
		this.bodyAttributes = bodyAttributes;
	}

	public Vector<IDAttribute> getColorAttributes() {
		return colorAttributes;
	}

	public void setColorAttributes(Vector<IDAttribute> colorAttributes) {
		this.colorAttributes = colorAttributes;
	}

	public boolean isEndMessage() {
		return endMessage;
	}

	public void setEndMessage(boolean endMessage) {
		this.endMessage = endMessage;
	}

}
