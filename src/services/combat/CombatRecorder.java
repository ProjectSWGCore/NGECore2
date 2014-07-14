package services.combat;

public class CombatRecorder {
	private long deathCount = 0;
	private long killCount = 0;
	private long assistCount = 0;
	private long damageDealt = 0;
	private long damageReceived = 0;
	private long healingDone = 0;
	private long healingReceived = 0;
	
	public long getDeathCount() {
		return deathCount;
	}
	public void setDeathCount(long deathCount) {
		this.deathCount = deathCount;
	}
	public long getKillCount() {
		return killCount;
	}
	public void setKillCount(long killCount) {
		this.killCount = killCount;
	}
	public long getAssistCount() {
		return assistCount;
	}
	public void setAssistCount(long assistCount) {
		this.assistCount = assistCount;
	}
	public long getDamageDealt() {
		return damageDealt;
	}
	public void setDamageDealt(long damageDealt) {
		this.damageDealt = damageDealt;
	}
	public long getDamageReceived() {
		return damageReceived;
	}
	public void setDamageReceived(long damageReceived) {
		this.damageReceived = damageReceived;
	}
	public long getHealingDone() {
		return healingDone;
	}
	public void setHealingDone(long healingDone) {
		this.healingDone = healingDone;
	}
	public long getHealingReceived() {
		return healingReceived;
	}
	public void setHealingReceived(long healingReceived) {
		this.healingReceived = healingReceived;
	}
}
