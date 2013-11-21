package resources.common;

public class BuffBuilder {
	private String statName;
	private String statAffects;
	private int maxTimesApplied;
	private int affectAmount;
	private String requiredExperience;
	
	private int entBonus;
	
	public BuffBuilder() {
		
	}

	public String getStatName() {
		return statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public String getStatAffects() {
		return statAffects;
	}

	public void setStatAffects(String statAffects) {
		this.statAffects = statAffects;
	}

	public int getMaxTimesApplied() {
		return maxTimesApplied;
	}

	public void setMaxTimesApplied(int maxTimesApplied) {
		this.maxTimesApplied = maxTimesApplied;
	}

	public int getAffectAmount() {
		return affectAmount;
	}

	public void setAffectAmount(int affectAmount) {
		this.affectAmount = affectAmount;
	}

	public String getRequiredExperience() {
		return requiredExperience;
	}

	public void setRequiredExperience(String requiredExperience) {
		this.requiredExperience = requiredExperience;
	}
	
	public int getTotalAffected() {
		int totalAffected = getMaxTimesApplied() + getAffectAmount();
		return totalAffected + getEntBonus();
	}

	public int getEntBonus() {
		return entBonus;
	}

	public void setEntBonus(int entBonus) {
		this.entBonus = entBonus;
	}
}
