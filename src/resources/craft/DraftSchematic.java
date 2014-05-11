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
package resources.craft;

import java.io.Serializable;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.Delta;

public class DraftSchematic extends Delta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int serverSchematicCrc = 0;
	private int schematicCrc = 0;
	private int amount = 1;
	
	public DraftSchematic(int serverSchematicCrc, int schematicCrc, int amount) {
		this.serverSchematicCrc = serverSchematicCrc;
		this.schematicCrc = schematicCrc;
		this.amount = amount;
	}
	
	public DraftSchematic(int serverSchematicCrc, int schematicCrc) {
		this.serverSchematicCrc = serverSchematicCrc;
		this.schematicCrc = schematicCrc;
	}
	
	public DraftSchematic() {
		
	}
	
	public int getServerSchematicCrc() {
		synchronized(objectMutex) {
			return serverSchematicCrc;
		}
	}
	
	public int getSchematicCrc() {
		synchronized(objectMutex) {
			return schematicCrc;
		}
	}
	
	public int getAmount() {
		synchronized(objectMutex) {
			return amount;
		}
	}
	
	public void setAmount(int amount) {
		synchronized(objectMutex) {
			this.amount = amount;
		}
	}
	
	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = createBuffer(12);
			buffer.putInt(serverSchematicCrc);
			buffer.putInt(schematicCrc);
			buffer.putInt(amount);
			return buffer.array();
		}
	}
	
}
