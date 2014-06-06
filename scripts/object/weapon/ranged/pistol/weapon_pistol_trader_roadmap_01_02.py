import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_pistol_trader_roadmap_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_pistol_trader_roadmap_01_02')
	object.setStringAttribute('class_required', 'Trader')
	object.setIntAttribute('required_combat_level', 62)	
	object.setAttackSpeed(0.4);
	object.setMaxRange(35);
	object.setDamageType("energy");
	object.setMinDamage(250);
	object.setMaxDamage(500);
	object.setWeaponType(2);
	return