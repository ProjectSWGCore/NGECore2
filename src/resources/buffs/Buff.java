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
package resources.buffs;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;

import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.resources.objects.Delta;

public class Buff extends Delta {
	
	private static final long serialVersionUID = 1L;
	
	private String group1 = "", group2 = "";
	private int priority = 0;
	private float duration = 0;
	private String buffName = "";
	private long bufferId = 0;
	private long buffeeId = 0;
	private String effect1Name = "", effect2Name = "", effect3Name = "", effect4Name = "", effect5Name = "";
	private float effect1Value, effect2Value, effect3Value, effect4Value, effect5Value;
	private String callback = "";
	private String particleEffect = "";
	private boolean isDebuff = false;
	private boolean removeOnDeath = false;
	private boolean isRemovableByPlayer = false;
	private int maxStacks = 0;
	private boolean isPersistent = false;
	private boolean removeOnRespec = false;
	private boolean aiRemoveOnEndCombat = false;
	private boolean decayOnPvPDeath = false;
	private long startTime = 0;
	private int totalPlayTime = 0;
	private byte decayCounter = 0;
	private transient ScheduledFuture<?> removalTask;
	private int stacks = 1;
	private long groupBufferId = 0;
	
	public Buff(Buff baseBuff, long bufferId, long buffeeId) {
		this.bufferId = bufferId;
		this.buffeeId = buffeeId;
		this.buffName = baseBuff.getBuffName();
		this.group1 = baseBuff.getGroup1();
		this.group2 = baseBuff.getGroup2();
		this.priority = baseBuff.getPriority();
		this.duration = baseBuff.getDuration();
		this.effect1Name = baseBuff.getEffect1Name();
		this.effect1Value = baseBuff.getEffect1Value();
		this.effect2Name = baseBuff.getEffect2Name();
		this.effect2Value = baseBuff.getEffect2Value();
		this.effect3Name = baseBuff.getEffect3Name();
		this.effect3Value = baseBuff.getEffect3Value();
		this.effect4Name = baseBuff.getEffect4Name();
		this.effect4Value = baseBuff.getEffect4Value();
		this.effect5Name = baseBuff.getEffect5Name();
		this.effect5Value = baseBuff.getEffect5Value();
		this.callback = baseBuff.getCallback();
		this.particleEffect = baseBuff.getParticleEffect();
		this.isDebuff = baseBuff.isDebuff();
		this.removeOnDeath = baseBuff.isRemoveOnDeath();
		this.isRemovableByPlayer = baseBuff.isRemovableByPlayer();
		this.maxStacks = baseBuff.getMaxStacks();
		this.isPersistent = baseBuff.isPersistent();
		this.removeOnRespec = baseBuff.isRemoveOnRespec();
		this.aiRemoveOnEndCombat = baseBuff.isAiRemoveOnEndCombat();
		this.decayOnPvPDeath = baseBuff.isDecayOnPvPDeath();
	}
	
	public Buff(String buffName, long bufferId, long buffeeId) {
		this.bufferId = bufferId;
		this.buffeeId = buffeeId;
		this.buffName = buffName;
		
		DatatableVisitor visitor;
		
		try {
			visitor = ClientFileManager.loadFile("datatables/buff/buff.iff", DatatableVisitor.class);
			
			for (int i = 0; i < visitor.getRowCount(); i++) {
				if (visitor.getObject(i, 0) != null) {
					if (((String) visitor.getObject(i, 0)).equalsIgnoreCase(buffName)) {
						group1 = (String) visitor.getObject(i, 1);
						group2 = (String) visitor.getObject(i, 2);
						priority = (int) visitor.getObject(i, 4);
						duration = (Float) visitor.getObject(i, 6);
						effect1Name = (String) visitor.getObject(i, 7);
						effect1Value = (Float) visitor.getObject(i, 8);
						effect2Name = (String) visitor.getObject(i, 9);
						effect2Value = (Float) visitor.getObject(i, 10);
						effect3Name = (String) visitor.getObject(i, 11);
						effect3Value = (Float) visitor.getObject(i, 12);
						effect4Name = (String) visitor.getObject(i, 13);
						effect4Value = (Float) visitor.getObject(i, 14);
						effect5Name = (String) visitor.getObject(i, 15);
						effect5Value = (Float) visitor.getObject(i, 16);
						callback = (String) visitor.getObject(i, 18);
						particleEffect = (String) visitor.getObject(i, 19);
						isDebuff = (Boolean) visitor.getObject(i, 22);
						removeOnDeath = (Integer) visitor.getObject(i, 25) != 0;
						isRemovableByPlayer = (Integer) visitor.getObject(i, 26) != 0;
						maxStacks = (Integer) visitor.getObject(i, 28);
						isPersistent = (Integer) visitor.getObject(i, 29) != 0;
						removeOnRespec = (Integer) visitor.getObject(i, 31) != 0;
						aiRemoveOnEndCombat = (Integer) visitor.getObject(i, 32) != 0;
						decayOnPvPDeath = (Integer) visitor.getObject(i, 33) != 0;
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public Buff() {
		
	}
	
	public byte[] getBytes() {
		// If getObject ever returns null here, it means there's a major bug with objects being in quadtree but not in objectList.

		if (NGECore.getInstance().objectService.getObject(buffeeId)==null){
			System.err.println("FATAL ERROR BUFFED CREATURE NOT IN OBJECTLIST");
			return new byte[]{};
		}
				
		CreatureObject cre = (CreatureObject)NGECore.getInstance().objectService.getObject(buffeeId);
		// NPCs should be considered here to
		if (cre.isPlayer()){
			PlayerObject player = (PlayerObject) NGECore.getInstance().objectService.getObject(buffeeId).getSlottedObject("ghost");
			long lastPlayTimeUpdate = ((player == null) ? 0L : player.getLastPlayTimeUpdate());
			int remainingDuration = getRemainingDuration();
			
			synchronized(objectMutex) {
				totalPlayTime = ((player == null) ? 0 : (int) (totalPlayTime + (System.currentTimeMillis() - lastPlayTimeUpdate) / 1000));
				IoBuffer buffer = createBuffer(24);
				buffer.putInt((int) ((duration > 0) ? (totalPlayTime + remainingDuration) : -1));		
				buffer.putInt(0);
				buffer.putInt((int) duration);
				buffer.putLong(bufferId);
				buffer.putInt(stacks);
				buffer.flip();
				return buffer.array();
			}
		} else {
			int remainingDuration = getRemainingDuration();
			synchronized(objectMutex) {
				totalPlayTime = 0;
				IoBuffer buffer = createBuffer(24);
				buffer.putInt((int) ((duration > 0) ? (totalPlayTime + remainingDuration) : -1));		
				buffer.putInt(0);
				buffer.putInt((int) duration);
				buffer.putLong(bufferId);
				buffer.putInt(stacks);
				buffer.flip();
				return buffer.array();
			}
		}
	}
	
	public String getGroup1() {
		synchronized(objectMutex) {
			return group1;
		}
	}
	
	public void setGroup1(String group1) {
		synchronized(objectMutex) {
			this.group1 = group1;
		}
	}
	
	public String getGroup2() {
		synchronized(objectMutex) {
			return group2;
		}
	}
	
	public void setGroup2(String group2) {
		synchronized(objectMutex) {
			this.group2 = group2;
		}
	}
	
	public int getPriority() {
		synchronized(objectMutex) {
			return priority;
		}
	}
	
	public void setPriority(int priority) {
		synchronized(objectMutex) {
			this.priority = priority;
		}
	}
	
	public float getDuration() {
		synchronized(objectMutex) {
			return duration;
		}
	}
	
	public void setDuration(float duration) {
		synchronized(objectMutex) {
			this.duration = duration;
		}
	}
	
	public String getBuffName() {
		synchronized(objectMutex) {
			return buffName;
		}
	}
	
	public void setBuffName(String buffName) {
		synchronized(objectMutex) {
			this.buffName = buffName;
		}
	}
	
	public long getBufferId() {
		synchronized(objectMutex) {
			return bufferId;
		}
	}
	
	public void setBufferId(long bufferId) {
		synchronized(objectMutex) {
			this.bufferId = bufferId;
		}
	}
	
	public long getBuffeeId() {
		synchronized(objectMutex) {
			return buffeeId;
		}
	}
	
	public void setBuffeeId(long buffeeId) {
		synchronized(objectMutex) {
			this.buffeeId = buffeeId;
		}
	}
	
	public String getEffect1Name() {
		synchronized(objectMutex) {
			return effect1Name;
		}
	}
	
	public void setEffect1Name(String effect1Name) {
		synchronized(objectMutex) {
			this.effect1Name = effect1Name;
		}
	}
	
	public String getEffect2Name() {
		synchronized(objectMutex) {
			return effect2Name;
		}
	}
	
	public void setEffect2Name(String effect2Name) {
		synchronized(objectMutex) {
			this.effect2Name = effect2Name;
		}
	}
	
	public String getEffect3Name() {
		synchronized(objectMutex) {
			return effect3Name;
		}
	}
	
	public void setEffect3Name(String effect3Name) {
		synchronized(objectMutex) {
			this.effect3Name = effect3Name;
		}
	}
	
	public String getEffect4Name() {
		synchronized(objectMutex) {
			return effect4Name;
		}
	}
	
	public void setEffect4Name(String effect4Name) {
		synchronized(objectMutex) {
			this.effect4Name = effect4Name;
		}
	}
	
	public String getEffect5Name() {
		synchronized(objectMutex) {
			return effect5Name;
		}
	}
	
	public void setEffect5Name(String effect5Name) {
		synchronized(objectMutex) {
			this.effect5Name = effect5Name;
		}
	}
	
	public float getEffect1Value() {
		synchronized(objectMutex) {
			return effect1Value;
		}
	}
	
	public void setEffect1Value(float effect1Value) {
		synchronized(objectMutex) {
			this.effect1Value = effect1Value;
		}
	}
	
	public float getEffect2Value() {
		synchronized(objectMutex) {
			return effect2Value;
		}
	}
	
	public void setEffect2Value(float effect2Value) {
		synchronized(objectMutex) {
			this.effect2Value = effect2Value;
		}
	}
	
	public float getEffect3Value() {
		synchronized(objectMutex) {
			return effect3Value;
		}
	}
	
	public void setEffect3Value(float effect3Value) {
		synchronized(objectMutex) {
			this.effect3Value = effect3Value;
		}
	}
	
	public float getEffect4Value() {
		synchronized(objectMutex) {
			return effect4Value;
		}
	}
	
	public void setEffect4Value(float effect4Value) {
		synchronized(objectMutex) {
			this.effect4Value = effect4Value;
		}
	}
	
	public float getEffect5Value() {
		synchronized(objectMutex) {
			return effect5Value;
		}
	}
	
	public void setEffect5Value(float effect5Value) {
		synchronized(objectMutex) {
			this.effect5Value = effect5Value;
		}
	}
	
	public String getCallback() {
		synchronized(objectMutex) {
			return callback;
		}
	}
	
	public void setCallback(String callback) {
		synchronized(objectMutex) {
			this.callback = callback;
		}
	}
	
	public String getParticleEffect() {
		synchronized(objectMutex) {
			return particleEffect;
		}
	}
	
	public void setParticleEffect(String particleEffect) {
		synchronized(objectMutex) {
			this.particleEffect = particleEffect;
		}
	}
	
	public boolean isDebuff() {
		synchronized(objectMutex) {
			return isDebuff;
		}
	}
	
	public void setDebuff(boolean isDebuff) {
		synchronized(objectMutex) {
			this.isDebuff = isDebuff;
		}
	}
	
	public boolean isRemoveOnDeath() {
		synchronized(objectMutex) {
			return removeOnDeath;
		}
	}
	
	public void setRemoveOnDeath(boolean removeOnDeath) {
		synchronized(objectMutex) {
			this.removeOnDeath = removeOnDeath;
		}
	}
	
	public boolean isRemovableByPlayer() {
		synchronized(objectMutex) {
			return isRemovableByPlayer;
		}
	}
	
	public void setRemovableByPlayer(boolean isRemovableByPlayer) {
		synchronized(objectMutex) {
			this.isRemovableByPlayer = isRemovableByPlayer;
		}
	}
	
	public int getMaxStacks() {
		synchronized(objectMutex) {
			return maxStacks;
		}
	}
	
	public void setMaxStacks(int maxStacks) {
		synchronized(objectMutex) {
			this.maxStacks = maxStacks;
		}
	}
	
	public boolean isPersistent() {
		synchronized(objectMutex) {
			return isPersistent;
		}
	}
	
	public void setPersistent(boolean isPersistent) {
		synchronized(objectMutex) {
			this.isPersistent = isPersistent;
		}
	}
	
	public boolean isRemoveOnRespec() {
		synchronized(objectMutex) {
			return removeOnRespec;
		}
	}
	
	public void setRemoveOnRespec(boolean removeOnRespec) {
		synchronized(objectMutex) {
			this.removeOnRespec = removeOnRespec;
		}
	}
	
	public boolean isAiRemoveOnEndCombat() {
		synchronized(objectMutex) {
			return aiRemoveOnEndCombat;
		}
	}
	
	public void setAiRemoveOnEndCombat(boolean aiRemoveOnEndCombat) {
		synchronized(objectMutex) {
			this.aiRemoveOnEndCombat = aiRemoveOnEndCombat;
		}
	}
	
	public boolean isDecayOnPvPDeath() {
		synchronized(objectMutex) {
			return decayOnPvPDeath;
		}
	}
	
	public void setDecayOnPvPDeath(boolean decayOnPvPDeath) {
		synchronized(objectMutex) {
			this.decayOnPvPDeath = decayOnPvPDeath;
		}
	}
	
	public void setStartTime() {
		synchronized(objectMutex) {
			this.startTime = System.currentTimeMillis();
		}
	}
	
	public long getStartTime() {
		synchronized(objectMutex) {
			return startTime;
		}
	}
	
	public int getRemainingDuration() {
		synchronized(objectMutex) {
			long currentTime = System.currentTimeMillis();
			long timeDiff = (currentTime - startTime) / 1000;
			int remaining = (int) (duration - timeDiff);
			
			for (int i = 0; i < decayCounter; i++) {
				remaining /= 2;
			}
			
			return remaining;
		}
	}
	
	public int getTotalPlayTime() {
		synchronized(objectMutex) {
			return totalPlayTime;
		}
	}
	
	public void setTotalPlayTime(int totalPlayTime) {
		synchronized(objectMutex) {
			this.totalPlayTime = totalPlayTime;
		}
	}
	
	public byte getDecayCounter() {
		synchronized(objectMutex) {
			return decayCounter;
		}
	}
	
	public void incDecayCounter() {
		synchronized(objectMutex) {
			this.decayCounter++;
		}
	}
	
	public ScheduledFuture<?> getRemovalTask() {
		return removalTask;
	}
	
	public void setRemovalTask(ScheduledFuture<?> removalTask) {
		this.removalTask = removalTask;
	}
	
	public void updateRemovalTask() {
		if (removalTask == null) {
			return;
		}
		
		removalTask.cancel(true);
		
		final NGECore core = NGECore.getInstance();
		final CreatureObject creature = (CreatureObject) core.objectService.getObject(getBuffeeId());
		
		if (creature == null) {
			return;
		}
		
		ScheduledFuture<?> task = Executors.newScheduledThreadPool(1).schedule(new Runnable() {
			
			@Override
			public void run() {
				try {
					if (creature!=null)
						core.buffService.removeBuffFromCreature(creature, Buff.this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}, (long) getRemainingDuration(), TimeUnit.SECONDS);
		
		setRemovalTask(task);
	}
	
	public int getStacks() {
		synchronized(objectMutex) {
			return stacks;
		}
	}
	
	public void setStacks(int stacks) {
		synchronized(objectMutex) {
			this.stacks = stacks;
		}
	}
	
	public boolean isGroupBuff() {
		synchronized(objectMutex) {
			return effect1Name.equals("group");
		}
	}
	
	public long getGroupBufferId() {
		synchronized(objectMutex) {
			return groupBufferId;
		}
	}
	
	public void setGroupBufferId(long groupBufferId) {
		synchronized(objectMutex) {
			this.groupBufferId = groupBufferId;
		}
	}
	
}
