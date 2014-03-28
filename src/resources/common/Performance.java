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
package resources.common;

public class Performance {

	
	private String performanceName;
	private int instrumentAudioId;
	private String requiredSong;
	private String requiredInstrument;
	private String requiredDance;
	private int danceVisualId;
	private int actionPointsPerLoop;
	private float loopDuration;
	private int type;
	private int baseXp;
	private int flourishXpMod;
	private int healMindWound;
	private int healShockWound;
	private String requiredSkillMod;
	private int requiredSkillModValue;
	private String mainloop;
	private String flourish1;
	private String flourish2;
	private String flourish3;
	private String flourish4;
	private String flourish5;
	private String flourish6;
	private String flourish7;
	private String flourish8;
	private String intro;
	private String outro;
	private int lineNumber;
	
	public Performance() {
		
	}

	public int getBaseXp() {
		return baseXp;
	}

	public void setBaseXp(int baseXp) {
		this.baseXp = baseXp;
	}
	
	public String getPerformanceName() {
		return performanceName;
	}

	public void setPerformanceName(String performanceName) {
		this.performanceName = performanceName;
	}

	public int getInstrumentAudioId() {
		return instrumentAudioId;
	}

	public void setInstrumentAudioId(int instrumentAudioId) {
		this.instrumentAudioId = instrumentAudioId;
	}

	public int getRequiredSkillModValue() {
		return requiredSkillModValue;
	}

	public void setRequiredSkillModValue(int requiredSkillModValue) {
		this.requiredSkillModValue = requiredSkillModValue;
	}

	public String getRequiredSong() {
		return requiredSong;
	}

	public void setRequiredSong(String requiredSong) {
		this.requiredSong = requiredSong;
	}

	public String getRequiredInstrument() {
		return requiredInstrument;
	}

	public void setRequiredInstrument(String requiredInstrument) {
		this.requiredInstrument = requiredInstrument;
	}

	public String getRequiredDance() {
		return requiredDance;
	}

	public void setRequiredDance(String requiredDance) {
		this.requiredDance = requiredDance;
	}

	public int getDanceVisualId() {
		return danceVisualId;
	}

	public void setDanceVisualId(int danceVisualId) {
		this.danceVisualId = danceVisualId;
	}

	public int getActionPointsPerLoop() {
		return actionPointsPerLoop;
	}

	public void setActionPointsPerLoop(int actionPointsPerLoop) {
		this.actionPointsPerLoop = actionPointsPerLoop;
	}

	public float getLoopDuration() {
		return loopDuration;
	}

	public void setLoopDuration(float loopDuration) {
		this.loopDuration = loopDuration;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getFlourishXpMod() {
		return flourishXpMod;
	}

	public void setFlourishXpMod(int flourishXpMod) {
		this.flourishXpMod = flourishXpMod;
	}

	public int getHealMindWound() {
		return healMindWound;
	}

	public void setHealMindWound(int healMindWound) {
		this.healMindWound = healMindWound;
	}

	public int getHealShockWound() {
		return healShockWound;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}

	public void setHealShockWound(int healShockWound) {
		this.healShockWound = healShockWound;
	}

	public String getRequiredSkillMod() {
		return requiredSkillMod;
	}

	public void setRequiredSkillMod(String requiredSkillMod) {
		this.requiredSkillMod = requiredSkillMod;
	}

	public String getMainloop() {
		return mainloop;
	}

	public void setMainloop(String mainloop) {
		this.mainloop = mainloop;
	}

	public String getFlourish1() {
		return flourish1;
	}

	public void setFlourish1(String flourish1) {
		this.flourish1 = flourish1;
	}

	public String getFlourish2() {
		return flourish2;
	}

	public void setFlourish2(String flourish2) {
		this.flourish2 = flourish2;
	}

	public String getFlourish3() {
		return flourish3;
	}

	public void setFlourish3(String flourish3) {
		this.flourish3 = flourish3;
	}

	public String getFlourish4() {
		return flourish4;
	}

	public void setFlourish4(String flourish4) {
		this.flourish4 = flourish4;
	}

	public String getFlourish5() {
		return flourish5;
	}

	public void setFlourish5(String flourish5) {
		this.flourish5 = flourish5;
	}

	public String getFlourish6() {
		return flourish6;
	}

	public void setFlourish6(String flourish6) {
		this.flourish6 = flourish6;
	}

	public String getFlourish7() {
		return flourish7;
	}

	public void setFlourish7(String flourish7) {
		this.flourish7 = flourish7;
	}

	public String getFlourish8() {
		return flourish8;
	}

	public void setFlourish8(String flourish8) {
		this.flourish8 = flourish8;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getOutro() {
		return outro;
	}

	public void setOutro(String outro) {
		this.outro = outro;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
}
