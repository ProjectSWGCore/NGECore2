import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_polearm_02_03')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_polearm_02_03')
	object.setIntAttribute('required_combat_level', 1)
	object.setFloatAttribute('cat_wpn_damage.wpn_attack_speed', 1)
	object.setStringAttribute('cat_wpn_damage.wpn_damage_type', 'Kinetic')
	object.setStringAttribute('cat_wpn_damage.damage', '43-75')
	object.setStringAttribute('cat_wpn_damage.wpn_range', '0-5m')
	object.setStringAttribute('cat_wpn_damage.wpn_category', 'Polearm')
	object.setIntAttribute('cat_wpn_damage.dps', object.getDamagePerSecond())
	return