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
package resources.objects;

import java.nio.ByteOrder;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;

import resources.objects.creature.CreatureObject;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.resources.common.CRC;

@Persistent(version=10)
public class Buff implements IDelta {
	
	@NotPersistent
	private SimpleBufferAllocator bufferPool = new SimpleBufferAllocator();
	private String group1, group2;
	private int priority;
	private float duration;
	private String buffName;
	private long ownerId;
	private String effect1Name, effect2Name, effect3Name, effect4Name, effect5Name;
	private float effect1Value, effect2Value, effect3Value, effect4Value, effect5Value;
	private String callback;
	private String particleEffect;
	private boolean isDebuff;
	private boolean removeOnDeath;
	private boolean isRemovableByPlayer;
	private int maxStacks;
	private boolean isPersistent;
	private boolean removeOnRespec;
	private boolean aiRemoveOnEndCombat;
	private boolean decayOnPvPDeath;
	private long startTime;
	private int totalPlayTime;
	private byte decayCounter = 0;
	@NotPersistent
	private ScheduledFuture<?> removalTask;
	private int stacks = 1;
	private long groupBufferId;
	private int buffCRC;
	
	public Buff(Buff baseBuff, long ownerId) {
		this.buffName = baseBuff.getBuffName();
		this.buffCRC = baseBuff.getBuffCRC();
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
	
	/*public Buff(String buffName, long ownerId) {
		
		this.buffName = buffName;
		this.ownerId = ownerId;
		this.buffCRC = CRC.StringtoCRC(buffName);

		DatatableVisitor visitor;
		
		try {
			
			visitor = ClientFileManager.loadFile("datatables/buff/buff.iff", DatatableVisitor.class);
			for(int i = 0; i < visitor.getRowCount(); i++) {
			
			if(visitor.getObject(i, 0) != null)
				if(((String) visitor.getObject(i, 0)).equalsIgnoreCase(buffName)) {
					
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
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}*/

	public Buff() { }

	@Override
	public byte[] getBytes() {
		
		IoBuffer buffer = bufferPool.allocate(28, false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putInt(CRC.StringtoCRC(buffName.toLowerCase()));
		if(duration > 0) {
			buffer.putInt((int) (totalPlayTime + getRemainingDuration()));		
			buffer.putInt(0);
			buffer.putInt((int) duration);
		} else {
			buffer.putInt(-1);
           	buffer.putInt(0);
			buffer.putInt(0);
		}
		buffer.putLong(ownerId);
		buffer.putInt(stacks);	// stacks
		
		buffer.flip();

		return buffer.array();
		
	}

	public String getGroup1() {
		return group1;
	}
	
	public void setGroup1(String group1) {
		this.group1 = group1;
	}
 
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public String getBuffName() {
		return buffName;
	}

	public void setBuffName(String buffName) {
		this.buffName = buffName;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getEffect1Name() {
		return effect1Name;
	}

	public void setEffect1Name(String effect1Name) {
		this.effect1Name = effect1Name;
	}

	public String getEffect2Name() {
		return effect2Name;
	}

	public void setEffect2Name(String effect2Name) {
		this.effect2Name = effect2Name;
	}

	public String getEffect3Name() {
		return effect3Name;
	}

	public void setEffect3Name(String effect3Name) {
		this.effect3Name = effect3Name;
	}

	public String getEffect4Name() {
		return effect4Name;
	}

	public void setEffect4Name(String effect4Name) {
		this.effect4Name = effect4Name;
	}

	public String getEffect5Name() {
		return effect5Name;
	}

	public void setEffect5Name(String effect5Name) {
		this.effect5Name = effect5Name;
	}

	public float getEffect1Value() {
		return effect1Value;
	}

	public void setEffect1Value(float effect1Value) {
		this.effect1Value = effect1Value;
	}

	public float getEffect2Value() {
		return effect2Value;
	}

	public void setEffect2Value(float effect2Value) {
		this.effect2Value = effect2Value;
	}

	public float getEffect3Value() {
		return effect3Value;
	}

	public void setEffect3Value(float effect3Value) {
		this.effect3Value = effect3Value;
	}

	public float getEffect4Value() {
		return effect4Value;
	}

	public void setEffect4Value(float effect4Value) {
		this.effect4Value = effect4Value;
	}

	public float getEffect5Value() {
		return effect5Value;
	}

	public void setEffect5Value(float effect5Value) {
		this.effect5Value = effect5Value;
	}
	
	public String getCallback() {
		return callback;
	}
	
	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getParticleEffect() {
		return particleEffect;
	}

	public void setParticleEffect(String particleEffect) {
		this.particleEffect = particleEffect;
	}

	public boolean isDebuff() {
		return isDebuff;
	}

	public void setDebuff(boolean isDebuff) {
		this.isDebuff = isDebuff;
	}

	public boolean isRemoveOnDeath() {
		return removeOnDeath;
	}

	public void setRemoveOnDeath(boolean removeOnDeath) {
		this.removeOnDeath = removeOnDeath;
	}

	public boolean isRemovableByPlayer() {
		return isRemovableByPlayer;
	}

	public void setRemovableByPlayer(boolean isRemovableByPlayer) {
		this.isRemovableByPlayer = isRemovableByPlayer;
	}

	public int getMaxStacks() {
		return maxStacks;
	}

	public void setMaxStacks(int maxStacks) {
		this.maxStacks = maxStacks;
	}

	public boolean isPersistent() {
		return isPersistent;
	}

	public void setPersistent(boolean isPersistent) {
		this.isPersistent = isPersistent;
	}

	public boolean isRemoveOnRespec() {
		return removeOnRespec;
	}

	public void setRemoveOnRespec(boolean removeOnRespec) {
		this.removeOnRespec = removeOnRespec;
	}

	public boolean isAiRemoveOnEndCombat() {
		return aiRemoveOnEndCombat;
	}

	public void setAiRemoveOnEndCombat(boolean aiRemoveOnEndCombat) {
		this.aiRemoveOnEndCombat = aiRemoveOnEndCombat;
	}

	public boolean isDecayOnPvPDeath() {
		return decayOnPvPDeath;
	}

	public void setDecayOnPvPDeath(boolean decayOnPvPDeath) {
		this.decayOnPvPDeath = decayOnPvPDeath;
	}

	public void setStartTime() {
		this.startTime = System.currentTimeMillis();
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public int getRemainingDuration() {
		
		long currentTime = System.currentTimeMillis();
		long timeDiff = (currentTime - startTime) / 1000;
		int remaining = (int) (duration - timeDiff);
		for(int i = 0; i < decayCounter; i++) {
			remaining /= 2;
		}
		return remaining;
		
	}

	public int getTotalPlayTime() {
		return totalPlayTime;
	}

	public void setTotalPlayTime(int totalPlayTime) {
		this.totalPlayTime = totalPlayTime;
	}

	public byte getDecayCounter() {
		return decayCounter;
	}

	public void incDecayCounter() {
		this.decayCounter++;
	}

	public ScheduledFuture<?> getRemovalTask() {
		return removalTask;
	}

	public void setRemovalTask(ScheduledFuture<?> removalTask) {
		this.removalTask = removalTask;
	}
	
	public void updateRemovalTask() {
		
		if(removalTask == null)
			return;
		
		removalTask.cancel(true);
		
		final NGECore core = NGECore.getInstance();
		final CreatureObject owner = (CreatureObject) core.objectService.getObject(getOwnerId());
		
		if(owner == null)
			return;
		
		ScheduledFuture<?> task = Executors.newScheduledThreadPool(1).schedule(new Runnable() {
			
			@Override
			public void run() {
				
				core.buffService.removeBuffFromCreature(owner, Buff.this);
			
			}
			
		}, (long) getRemainingDuration(), TimeUnit.SECONDS);
		
		setRemovalTask(task);
		
	}

	public int getStacks() {
		return stacks;
	}

	public void setStacks(int stacks) {
		this.stacks = stacks;
	}
	
	public boolean isGroupBuff() {
		return effect1Name == null ? false : effect1Name.equals("group");
	}

	public long getGroupBufferId() {
		return groupBufferId;
	}

	public void setGroupBufferId(long groupBufferId) {
		this.groupBufferId = groupBufferId;
	}

	public String getGroup2() {
		return group2;
	}

	public void setGroup2(String group2) {
		this.group2 = group2;
	}

	public int getBuffCRC() {
		return buffCRC;
	}
	
	public void setBuffCRC(int buffCRC) {
		this.buffCRC = buffCRC;
	}

}
