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
package resources.z.exp.objects.object;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.buffer.IoBuffer;

import resources.common.Stf;
import resources.common.UString;
import resources.z.exp.objects.Baseline;
import resources.z.exp.objects.Builder;
import resources.z.exp.objects.ObjectMessageBuilder;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent
public class BaseObject extends SWGObject {
	
	@NotPersistent
	private ObjectMessageBuilder messageBuilder;
	
	protected Baseline baseline1;
	protected Baseline baseline3;
	protected Baseline baseline4;
	protected Baseline baseline6;
	protected Baseline baseline7;
	protected Baseline baseline8;
	protected Baseline baseline9;
	protected Baseline otherVariables;
	
	@NotPersistent
    private ScheduledExecutorService scheduler;
	
	private boolean ready = false;
	
	public BaseObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, position, orientation, Template);
		super.setCustomName("");
		if (super.getDetailFilename() == null) super.setDetailFilename("");
		if (super.getDetailName() == null) super.setDetailName("");
		initializeBaselines();
		otherVariables = getOtherVariables();
		ready = true;
	}
	
	public BaseObject() {
		super();
		
		scheduler = Executors.newScheduledThreadPool(2);
		
		// Makes sure this runs after the baselines have been restored from DB
		
		scheduler.schedule(new Runnable() {
			
			public void run() {
				while (!ready);
				initializeBaselines();
			}
			
		}, 0, TimeUnit.NANOSECONDS);
	}
	
	public void initializeBaselines() {		
		// Transform the structures of any baselines incase they've been altered
		
		if (otherVariables != null) {
			otherVariables.transformStructure(getOtherVariables());
		}
		
		if (baseline1 != null) {
			baseline1.transformStructure(getBaseline1());
			addBuilders(1);
		}
		
		if (baseline3 != null) {
			baseline3.transformStructure(getBaseline3());
			addBuilders(3);
		}
		
		if (baseline4 != null) {
			baseline4.transformStructure(getBaseline4());
			addBuilders(4);
		}
		
		if (baseline6 != null) {
			baseline6.transformStructure(getBaseline6());
			addBuilders(6);
		}
		
		if (baseline7 != null) {
			baseline7.transformStructure(getBaseline7());
			addBuilders(7);
		}
		
		if (baseline8 != null) {
			baseline8.transformStructure(getBaseline8());
			addBuilders(8);
		}
		
		if (baseline9 != null) {
			baseline9.transformStructure(getBaseline9());
			addBuilders(9);
		}
		
		// Inheriting objects initialize these only if needed, for efficiency
		
		initializeBaseline(3);
		initializeBaseline(6);
	}
	
	public void initializeBaseline(int viewType) {
		// Won't initialize any baselines if they've already been initialized
		
		switch (viewType) {
			case 1:
				if (baseline1 == null) {
					baseline1 = getBaseline1();
					addBuilders(viewType);
					getMessageBuilder().buildBaseline1(baseline1.getBaselineBuilders(), baseline1.getDeltaBuilders());
				}
				
				return;
			case 3:
				if (baseline3 == null) {
					baseline3 = getBaseline3();
					addBuilders(viewType);
					getMessageBuilder().buildBaseline3(baseline3.getBaselineBuilders(), baseline3.getDeltaBuilders());
				}
				
				return;
			case 4:
				if (baseline4 == null) {
					baseline4 = getBaseline4();
					addBuilders(viewType);
					getMessageBuilder().buildBaseline4(baseline4.getBaselineBuilders(), baseline4.getDeltaBuilders());
				}
				
				return;
			case 6:
				if (baseline6 == null) {
					baseline6 = getBaseline6();
					addBuilders(viewType);
					getMessageBuilder().buildBaseline6(baseline6.getBaselineBuilders(), baseline6.getDeltaBuilders());
				}
				
				return;
			case 7:
				if (baseline7 == null) {
					baseline7 = getBaseline7();
					addBuilders(viewType);
					getMessageBuilder().buildBaseline7(baseline7.getBaselineBuilders(), baseline7.getDeltaBuilders());
				}
				
				return;
			case 8:
				if (baseline8 == null) {
					baseline8 = getBaseline8();
					addBuilders(viewType);
					getMessageBuilder().buildBaseline8(baseline8.getBaselineBuilders(), baseline8.getDeltaBuilders());
				}
				
				return;
			case 9:
				if (baseline9 == null) {
					baseline9 = getBaseline9();
					addBuilders(viewType);
					getMessageBuilder().buildBaseline9(baseline9.getBaselineBuilders(), baseline9.getDeltaBuilders());
				}
			default:
				return;
		}
	}
	
	public void addBuilders(int viewType) {
		Baseline baseline = getBaseline(viewType);
		Map<Integer, Builder> baselineBuilders = new HashMap<Integer, Builder>();
		Map<Integer, Builder> deltaBuilders = new HashMap<Integer, Builder>();
		
		switch (viewType) {
			case 1:
				getMessageBuilder().buildBaseline1(baselineBuilders, deltaBuilders);
				break;
			case 3:
				getMessageBuilder().buildBaseline3(baselineBuilders, deltaBuilders);
				break;
			case 4:
				getMessageBuilder().buildBaseline4(baselineBuilders, deltaBuilders);
				break;
			case 6:
				getMessageBuilder().buildBaseline6(baselineBuilders, deltaBuilders);
				break;
			case 7:
				getMessageBuilder().buildBaseline7(baselineBuilders, deltaBuilders);
				break;
			case 8:
				getMessageBuilder().buildBaseline8(baselineBuilders, deltaBuilders);
				break;
			case 9:
				getMessageBuilder().buildBaseline9(baselineBuilders, deltaBuilders);
				break;
		}
		
		if (baselineBuilders.size() > 0) {
			baseline.setBaselineBuilders(baselineBuilders);
		}
		
		if (deltaBuilders.size() > 0) {
			baseline.setDeltaBuilders(deltaBuilders);
		}
	}
	
	public Baseline getOtherVariables() {
		Baseline baseline = new Baseline(this, 0);
		return baseline;
	}
	
	public Baseline getBaseline1() {
		Baseline baseline = new Baseline(this, 1);
		return baseline;
	}
	
	public Baseline getBaseline3() {
		Baseline baseline = new Baseline(this, 3);
		baseline.put("complexity", (float) 1);
		baseline.put("stf", new Stf(getStfFilename(), 0, getStfName()));
		baseline.put("customName", new UString(""));
		baseline.put("volume", 1);
		return baseline;
	}
	
	public Baseline getBaseline4() {
		Baseline baseline = new Baseline(this, 4);
		return baseline;
	}
	
	public Baseline getBaseline6() {
		Baseline baseline = new Baseline(this, 6);
		baseline.put("serverId", 0);
		baseline.put("detail", new Stf(getDetailFilename(), 0, getDetailName()));
		return baseline;
	}
	
	public Baseline getBaseline7() {
		Baseline baseline = new Baseline(this, 7);
		return baseline;
	}
	
	public Baseline getBaseline8() {
		Baseline baseline = new Baseline(this, 8);
		return baseline;
	}
	
	public Baseline getBaseline9() {
		Baseline baseline = new Baseline(this, 9);
		return baseline;
	}
	
	public float getComplexity() {
		synchronized(objectMutex) {
			return (float) baseline3.get("complexity");
		}
	}
	
	public void setComplexity(float complexity) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("complexity", complexity);
		}
		
		if (baseline3 != null) {
			notifyClients(buffer, true);
		}
	}
	
	public Stf getStf() {
		return (Stf) baseline3.get("stf");
	}
	
	public void setStfFilename(String stfFilename) {
		super.setStfFilename(stfFilename);
		
		if (baseline3 != null) {
			IoBuffer buffer;
			
			getStf().setStfFilename(stfFilename);
			
			synchronized(objectMutex) {
				buffer = baseline3.set("stf", getStf());
			}
			
			if (baseline3 != null) {
				notifyClients(buffer, true);
			}
		}
	}
	
	public int getStfSpacer() {
		synchronized(objectMutex) {
			return ((Stf) baseline3.get("stf")).getSpacer();
		}
	}
	
	public void setStfSpacer(int stfSpacer) {
		IoBuffer buffer;
		
		getStf().setSpacer(stfSpacer);
		
		synchronized(objectMutex) {
			buffer = baseline3.set("stf", getStf());
		}
		
		if (baseline3 != null) {
			notifyClients(buffer, true);
		}
	}
	
	public void setStfName(String stfName) {
		super.setStfName(stfName);
		
		if (baseline3 != null) {
			IoBuffer buffer;
			
			getStf().setStfName(stfName);
			
			synchronized(objectMutex) {
				buffer = baseline3.set("stf", getStf());
			}
			
			if (baseline3 != null) {
				notifyClients(buffer, true);
			}
		}
	}
	
	public void setCustomName(String customName) {
		super.setCustomName(customName);
		
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("customName", customName);
		}
		
		if (baseline3 != null) {
			notifyClients(buffer, true);
		}
	}
	
	public void setVolume(int volume) {
		super.setVolume(volume);
		
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("volume", volume);
		}
		
		if (baseline3 != null) {
			notifyClients(buffer, true);
		}
	}
	
	public void incrementVolume(int increase) {
		setVolume((getVolume() + increase));
	}
	
	public void decrementVolume(int decrease) {
		setVolume((((getVolume() - decrease) < 1) ? 0 : (getVolume() - decrease)));
	}
	
	public int getServerId() {
		synchronized(objectMutex) {
			return (int) baseline6.get("serverId");
		}
	}
	
	public void setServerId(int serverId) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("serverId", serverId);
		}
		
		if (baseline6 != null) {
			notifyClients(buffer, true);
		}
	}
	
	public Stf getDetail() {
		return (Stf) baseline3.get("detail");
	}
	
	public void setDetailFilename(String detailFilename) {
		super.setDetailFilename(detailFilename);
		
		if (baseline6 != null) {
			IoBuffer buffer;
			
			getDetail().setStfFilename(detailFilename);
			
			synchronized(objectMutex) {
				buffer = baseline6.set("detail", getDetail());
			}
			
			if (baseline6 != null) {
				notifyClients(buffer, true);
			}
		}
	}
	
	public int getDetailSpacer() {
		synchronized(objectMutex) {
			return ((Stf) baseline6.get("detail")).getSpacer();
		}
	}
	
	public void setDetailSpacer(int detailSpacer) {
		IoBuffer buffer;
		
		getDetail().setSpacer(detailSpacer);
		
		synchronized(objectMutex) {
			buffer = baseline6.set("detail", getDetail());
		}
		
		if (baseline6 != null) {
			notifyClients(buffer, true);
		}
	}
	
	public void setDetailName(String detailName) {
		super.setDetailName(detailName);
		
		if (baseline6 != null) {
			IoBuffer buffer;
			
			getDetail().setStfName(detailName);
			
			synchronized(objectMutex) {
				buffer = baseline6.set("detail", getDetail());
			}
			
			if (baseline6 != null) {
				notifyClients(buffer, true);
			}
		}
	}
	
	protected Baseline getBaseline(int viewType) {
		switch (viewType) {
			case 1: return baseline1;
			case 3: return baseline3;
			case 4: return baseline4;
			case 6: return baseline6;
			case 7: return baseline7;
			case 8: return baseline8;
			case 9: return baseline9;
			default: return null;
		}
	}
	
	/*
	 This should contain the method of notifying observers, as
	 it tends to vary depending on the type of object, which is
	 a problem for inheritance when the master object uses a
	 different method (ie. ITNO and PLAY).
	 */
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		notifyObservers(buffer, notifySelf);
	}
	
	public ObjectMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null) {
				messageBuilder = new ObjectMessageBuilder(this);
			}
			
			return messageBuilder;
		}
	}
	
	@Override
	public void sendBaselines(Client destination) {
		if (destination != null && destination.getSession() != null) {
			destination.getSession().write(baseline3.getBaseline());
			destination.getSession().write(baseline6.getBaseline());
		}
	}
	
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		switch (viewType) {
			case 1:
			case 4:
			case 7:
				if (getClient() != null) {
					buffer = getBaseline(viewType).createDelta(updateType, buffer.array());
					getClient().getSession().write(buffer);
				}
				
				return;
			case 3:
			case 6:
			case 8:
			case 9:
				notifyClients(getBaseline(viewType).createDelta(updateType, buffer.array()), true);
			default:
				return;
		}
	}
	
}
