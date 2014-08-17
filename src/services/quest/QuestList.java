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
package services.quest;

public class QuestList {
	private int level;
	private int tier;
	private int rewardExperienceAmount;
	private int rewardFactionAmount;
	private int rewardBankCredits;
	private int rewardLootCount;
	private int rewardLootCount2;
	private int rewardLootCount3;
	private int rewardExclusiveLootCount;
	private int rewardExclusiveLootCount2;
	private int rewardExclusiveLootCount3;
	private int rewardExclusiveLootCount4;
	private int rewardExclusiveLootCount5;
	private int rewardExclusiveLootCount6;
	private int rewardExclusiveLootCount7;
	private int rewardExclusiveLootCount8;
	private int rewardExclusiveLootCount9;
	private int rewardExclusiveLootCount10;
	private String type;
	private String journalEntryTitle;
	private String journalEntryDescription;
	private String journalEntryCompletionSummary;
	private String category;
	private String visible;
	private String prerequisteQuests;
	private String exclusionQuests;
	private String rewardExperienceType;
	private String rewardFactionType;
	private String rewardLootName;
	private String rewardLootName2;
	private String rewardLootName3;
	private String rewardExclusiveLootName;
	private String rewardExclusiveLootName2;
	private String rewardExclusiveLootName3;
	private String rewardExclusiveLootName4;
	private String rewardExclusiveLootName5;
	private String rewardExclusiveLootName6;
	private String rewardExclusiveLootName7;
	private String rewardExclusiveLootName8;
	private String rewardExclusiveLootName9;
	private String rewardExclusiveLootName10;
	private String rewardBadge;
	
	private boolean allowRepeats;
	private boolean completeWhenTasksComplete;
	
	public QuestList() { }
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getTier() {
		return tier;
	}
	public void setTier(int tier) {
		this.tier = tier;
	}
	public int getRewardExperienceAmount() {
		return rewardExperienceAmount;
	}
	public void setRewardExperienceAmount(int rewardExperienceAmount) {
		this.rewardExperienceAmount = rewardExperienceAmount;
	}
	public int getRewardFactionAmount() {
		return rewardFactionAmount;
	}
	public void setRewardFactionAmount(int rewardFactionAmount) {
		this.rewardFactionAmount = rewardFactionAmount;
	}
	public int getRewardBankCredits() {
		return rewardBankCredits;
	}
	public void setRewardBankCredits(int rewardBankCredits) {
		this.rewardBankCredits = rewardBankCredits;
	}
	public int getRewardLootCount() {
		return rewardLootCount;
	}
	public void setRewardLootCount(int rewardLootCount) {
		this.rewardLootCount = rewardLootCount;
	}
	public int getRewardLootCount2() {
		return rewardLootCount2;
	}
	public void setRewardLootCount2(int rewardLootCount2) {
		this.rewardLootCount2 = rewardLootCount2;
	}
	public int getRewardLootCount3() {
		return rewardLootCount3;
	}
	public void setRewardLootCount3(int rewardLootCount3) {
		this.rewardLootCount3 = rewardLootCount3;
	}
	public int getRewardExclusiveLootCount() {
		return rewardExclusiveLootCount;
	}
	public void setRewardExclusiveLootCount(int rewardExclusiveLootCount) {
		this.rewardExclusiveLootCount = rewardExclusiveLootCount;
	}
	public int getRewardExclusiveLootCount2() {
		return rewardExclusiveLootCount2;
	}
	public void setRewardExclusiveLootCount2(int rewardExclusiveLootCount2) {
		this.rewardExclusiveLootCount2 = rewardExclusiveLootCount2;
	}
	public int getRewardExclusiveLootCount3() {
		return rewardExclusiveLootCount3;
	}
	public void setRewardExclusiveLootCount3(int rewardExclusiveLootCount3) {
		this.rewardExclusiveLootCount3 = rewardExclusiveLootCount3;
	}
	public int getRewardExclusiveLootCount4() {
		return rewardExclusiveLootCount4;
	}
	public void setRewardExclusiveLootCount4(int rewardExclusiveLootCount4) {
		this.rewardExclusiveLootCount4 = rewardExclusiveLootCount4;
	}
	public int getRewardExclusiveLootCount5() {
		return rewardExclusiveLootCount5;
	}
	public void setRewardExclusiveLootCount5(int rewardExclusiveLootCount5) {
		this.rewardExclusiveLootCount5 = rewardExclusiveLootCount5;
	}
	public int getRewardExclusiveLootCount6() {
		return rewardExclusiveLootCount6;
	}
	public void setRewardExclusiveLootCount6(int rewardExclusiveLootCount6) {
		this.rewardExclusiveLootCount6 = rewardExclusiveLootCount6;
	}
	public int getRewardExclusiveLootCount7() {
		return rewardExclusiveLootCount7;
	}
	public void setRewardExclusiveLootCount7(int rewardExclusiveLootCount7) {
		this.rewardExclusiveLootCount7 = rewardExclusiveLootCount7;
	}
	public int getRewardExclusiveLootCount8() {
		return rewardExclusiveLootCount8;
	}
	public void setRewardExclusiveLootCount8(int rewardExclusiveLootCount8) {
		this.rewardExclusiveLootCount8 = rewardExclusiveLootCount8;
	}
	public int getRewardExclusiveLootCount9() {
		return rewardExclusiveLootCount9;
	}
	public void setRewardExclusiveLootCount9(int rewardExclusiveLootCount9) {
		this.rewardExclusiveLootCount9 = rewardExclusiveLootCount9;
	}
	public int getRewardExclusiveLootCount10() {
		return rewardExclusiveLootCount10;
	}
	public void setRewardExclusiveLootCount10(int rewardExclusiveLootCount10) {
		this.rewardExclusiveLootCount10 = rewardExclusiveLootCount10;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getJournalEntryTitle() {
		return journalEntryTitle;
	}
	public void setJournalEntryTitle(String journalEntryTitle) {
		this.journalEntryTitle = journalEntryTitle;
	}
	public String getJournalEntryDescription() {
		return journalEntryDescription;
	}
	public void setJournalEntryDescription(String journalEntryDescription) {
		this.journalEntryDescription = journalEntryDescription;
	}
	public String getJournalEntryCompletionSummary() {
		return journalEntryCompletionSummary;
	}
	public void setJournalEntryCompletionSummary(
			String journalEntryCompletionSummary) {
		this.journalEntryCompletionSummary = journalEntryCompletionSummary;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	public String getPrerequisteQuests() {
		return prerequisteQuests;
	}
	public void setPrerequisteQuests(String prerequisteQuests) {
		this.prerequisteQuests = prerequisteQuests;
	}
	public String getExclusionQuests() {
		return exclusionQuests;
	}
	public void setExclusionQuests(String exclusionQuests) {
		this.exclusionQuests = exclusionQuests;
	}
	public String getRewardExperienceType() {
		return rewardExperienceType;
	}
	public void setRewardExperienceType(String rewardExperienceType) {
		this.rewardExperienceType = rewardExperienceType;
	}
	public String getRewardFactionType() {
		return rewardFactionType;
	}
	public void setRewardFactionType(String rewardFactionType) {
		this.rewardFactionType = rewardFactionType;
	}
	public String getRewardLootName() {
		return rewardLootName;
	}
	public void setRewardLootName(String rewardLootName) {
		this.rewardLootName = rewardLootName;
	}
	public String getRewardLootName2() {
		return rewardLootName2;
	}
	public void setRewardLootName2(String rewardLootName2) {
		this.rewardLootName2 = rewardLootName2;
	}
	public String getRewardLootName3() {
		return rewardLootName3;
	}
	public void setRewardLootName3(String rewardLootName3) {
		this.rewardLootName3 = rewardLootName3;
	}
	public String getRewardExclusiveLootName() {
		return rewardExclusiveLootName;
	}
	public void setRewardExclusiveLootName(String rewardExclusiveLootName) {
		this.rewardExclusiveLootName = rewardExclusiveLootName;
	}
	public String getRewardExclusiveLootName2() {
		return rewardExclusiveLootName2;
	}
	public void setRewardExclusiveLootName2(String rewardExclusiveLootName2) {
		this.rewardExclusiveLootName2 = rewardExclusiveLootName2;
	}
	public String getRewardExclusiveLootName3() {
		return rewardExclusiveLootName3;
	}
	public void setRewardExclusiveLootName3(String rewardExclusiveLootName3) {
		this.rewardExclusiveLootName3 = rewardExclusiveLootName3;
	}
	public String getRewardExclusiveLootName4() {
		return rewardExclusiveLootName4;
	}
	public void setRewardExclusiveLootName4(String rewardExclusiveLootName4) {
		this.rewardExclusiveLootName4 = rewardExclusiveLootName4;
	}
	public String getRewardExclusiveLootName5() {
		return rewardExclusiveLootName5;
	}
	public void setRewardExclusiveLootName5(String rewardExclusiveLootName5) {
		this.rewardExclusiveLootName5 = rewardExclusiveLootName5;
	}
	public String getRewardExclusiveLootName6() {
		return rewardExclusiveLootName6;
	}
	public void setRewardExclusiveLootName6(String rewardExclusiveLootName6) {
		this.rewardExclusiveLootName6 = rewardExclusiveLootName6;
	}
	public String getRewardExclusiveLootName7() {
		return rewardExclusiveLootName7;
	}
	public void setRewardExclusiveLootName7(String rewardExclusiveLootName7) {
		this.rewardExclusiveLootName7 = rewardExclusiveLootName7;
	}
	public String getRewardExclusiveLootName8() {
		return rewardExclusiveLootName8;
	}
	public void setRewardExclusiveLootName8(String rewardExclusiveLootName8) {
		this.rewardExclusiveLootName8 = rewardExclusiveLootName8;
	}
	public String getRewardExclusiveLootName9() {
		return rewardExclusiveLootName9;
	}
	public void setRewardExclusiveLootName9(String rewardExclusiveLootName9) {
		this.rewardExclusiveLootName9 = rewardExclusiveLootName9;
	}
	public String getRewardExclusiveLootName10() {
		return rewardExclusiveLootName10;
	}
	public void setRewardExclusiveLootName10(String rewardExclusiveLootName10) {
		this.rewardExclusiveLootName10 = rewardExclusiveLootName10;
	}
	public String getRewardBadge() {
		return rewardBadge;
	}
	public void setRewardBadge(String rewardBadge) {
		this.rewardBadge = rewardBadge;
	}
	public boolean isAllowRepeats() {
		return allowRepeats;
	}
	public void setAllowRepeats(boolean allowRepeats) {
		this.allowRepeats = allowRepeats;
	}

	public boolean isCompleteWhenTasksComplete() {
		return completeWhenTasksComplete;
	}

	public void setCompleteWhenTasksComplete(boolean completeWhenTasksComplete) {
		this.completeWhenTasksComplete = completeWhenTasksComplete;
	}

}
