import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setStringAttribute('faction_restriction', 'Rebel')
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_pistol_pvp_rebel_general_reward_06_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_pistol_pvp_rebel_general_reward_06_01')
	object.setIntAttribute('no_trade', 1)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 15)
	object.setIntAttribute('required_combat_level', 75)
	object.setAttackSpeed(0.4)
	object.setMaxRange(35)
	object.setDamageType('energy')
	object.setElementalType('heat')
	object.setMinDamage(288)
	object.setMaxDamage(575)
	object.setWeaponType(WeaponType.PISTOL)
	return