import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_rifle_pvp_general_reward_06_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_rifle_pvp_general_reward_06_01')
	object.setStringAttribute('tier', '6')
	object.setIntAttribute('no_trade', 1)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 10)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 10)
	object.setStringAttribute('healing_combat_level_required', '75')
	object.setStringAttribute('required_skill', 'None')
	object.setDamageType("energy");
	object.setWeaponType(0);
	object.setAttackSpeed(0.8);
	object.setMinDamage(518);
	object.setMaxDamage(1035);
	object.setStringAttribute('wpn_elemental_type', 'Acid')
	object.setStringAttribute('wpn_elemental_value', '20')
	object.setMaxRange(64)	
	return