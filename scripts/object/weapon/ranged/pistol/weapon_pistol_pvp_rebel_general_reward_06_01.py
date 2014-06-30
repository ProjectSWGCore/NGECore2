import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setStringAttribute('faction_restriction', 'Rebel')
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_pistol_pvp_rebel_general_reward_06_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_pistol_pvp_rebel_general_reward_06_01')
	object.setAttackSpeed(0.4)
	object.setMaxRange(35)
	object.setDamageType('energy')
	object.setMinDamage(275)
	object.setMaxDamage(518)
	object.setWeaponType(WeaponType.PISTOL)
	return