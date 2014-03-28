import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_npe_carbine_bh_03_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_npe_carbine_bh_03_01')
	object.setStringAttribute('class_required', 'Bounty Hunter')
	object.setIntAttribute('required_combat_level', 1)
	object.setFloatAttribute('cat_wpn_damage.wpn_attack_speed', 0.6)
	object.setStringAttribute('cat_wpn_damage.wpn_damage_type', 'Energy')
	object.setStringAttribute('cat_wpn_damage.damage', '17-43')
	object.setStringAttribute('cat_wpn_damage.wpn_range', '0-35m')
	object.setStringAttribute('cat_wpn_damage.wpn_category', 'Carbine')
	object.setIntAttribute('cat_wpn_damage.dps', object.getDamagePerSecond())
	return