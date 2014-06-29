import sys

def setup(core, object):
	object.setStringAttribute('tier', '6')
	object.setIntAttribute('no_trade', 1)
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_carbine_pvp_general_reward_06_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_carbine_pvp_general_reward_06_01')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 10)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 10)
	object.setStringAttribute('faction_restriction', 'Rebel')
	object.setStringAttribute('required_skill', 'None')
	object.setDamageType("energy");
	object.setWeaponType(1);
	object.setAttackSpeed(0.6);
	object.setMinDamage(389);
	object.setMaxDamage(780);
	object.setStringAttribute('wpn_elemental_type', 'Cold')
	object.setStringAttribute('wpn_elemental_value', '20')
	object.setMaxRange(50);
	return