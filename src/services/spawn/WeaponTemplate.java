package services.spawn;

public class WeaponTemplate {
	
	private String template="";
	private float attackSpeed=0;
	private int weaponType=0;
	
	public WeaponTemplate(){
		
	}
	
	public WeaponTemplate(String template, int weaponType, float attackSpeed){
		this.template = template;
		this.weaponType = weaponType;
		this.attackSpeed = attackSpeed;
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
}
