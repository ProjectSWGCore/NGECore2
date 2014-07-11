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
package services.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import resources.common.ProsePackage;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity(version=1)
public class Mail implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private int mailId;
	private String senderName;
	private long recieverId;
	private String subject;
	private String message = "";
	private byte status;
	private int timeStamp;
	private List<WaypointAttachment> attachments = new ArrayList<WaypointAttachment>();
	private List<ProsePackage> proseAttachments = new ArrayList<ProsePackage>();
	
	public static final byte NEW = 0x4E;
	public static final byte READ = 0x52;
	public static final byte UNREAD = 0x55;
	
	
	public Mail() {
		
	}


	public int getMailId() {
		return mailId;
	}


	public void setMailId(int mailId) {
		this.mailId = mailId;
	}


	public String getSenderName() {
		return senderName;
	}


	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}


	public long getRecieverId() {
		return recieverId;
	}


	public void setRecieverId(long recieverId) {
		this.recieverId = recieverId;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public byte getStatus() {
		return status;
	}


	public void setStatus(byte status) {
		this.status = status;
	}


	public int getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}

	public List<WaypointAttachment> getWaypointAttachments() {
		return attachments;
	}

	public void setWaypointAttachments(List<WaypointAttachment> attachments) {
		this.attachments = attachments;
	}
	
	public void addWaypointAttachment(WaypointAttachment attachment) {
		attachments.add(attachment);
	}

	public List<ProsePackage> getProseAttachments() {
		return proseAttachments;
	}

	public void setProseAttachments(List<ProsePackage> proseAttachments) {
		this.proseAttachments = proseAttachments;
	}
	
	public void addProseAttachment(ProsePackage prose) {
		proseAttachments.add(prose);
	}

	public void init() {
		proseAttachments.forEach(ProsePackage::init);
	}
	
}
