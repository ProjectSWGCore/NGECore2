package services.spawn;

public class WeaponTemplate {
	
	private String template = "";
	private int weaponType = 0;
	private float attackSpeed = 0;
	private int minDamage = 0;
	private int maxDamage = 0;
	private float maxRange = 0;
	private String damageType = "";
	
	public WeaponTemplate(){
		
	}
	
	public WeaponTemplate(String template, int weaponType, float attackSpeed, float maxRange, int minDamage, int maxDamage, String damageType){
		this.template = template;
		this.weaponType = weaponType;
		this.attackSpeed = attackSpeed;
		this.maxRange = maxRange;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.damageType = damageType;
	}
	
	public WeaponTemplate(String template, int weaponType, float attackSpeed, float maxRange, String damageType){
		this.template = template;
		this.weaponType = weaponType;
		this.attackSpeed = attackSpeed;
		this.maxRange = maxRange;
		this.damageType = damageType;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public float getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(float attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public int getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(int weaponType) {
		this.weaponType = weaponType;
	}

	public float getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(float maxRange) {
		this.maxRange = maxRange;
	}

	public int getMinDamage() {
		return minDamage;
	}

	public void setMinDamage(int minDamage) {
		this.minDamage = minDamage;
	}

	public int getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}

	public String getDamageType() {
		return damageType;
	}

	public void setDamageType(String damageType) {
		this.damageType = damageType;
	}
}
