import sys

def setup(core, object):
	object.setIntAttribute('required_combat_level', 88)
	object.setFloatAttribute('cat_wpn_damage.wpn_attack_speed', 1)
	object.setStringAttribute('cat_wpn_damage.wpn_damage_type', 'Energy')
	object.setStringAttribute('cat_wpn_damage.damage', '900-1350')
	object.setStringAttribute('cat_wpn_damage.wpn_range', '0-5m')
	object.setStringAttribute('cat_wpn_damage.wpn_category', '2-Handed')
	object.setIntAttribute('cat_wpn_damage.dps', object.getDamagePerSecond())
	return