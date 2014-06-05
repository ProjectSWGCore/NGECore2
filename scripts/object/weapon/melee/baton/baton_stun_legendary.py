import sys

def setup(core, object):
	object.setIntAttribute('required_combat_level', 88)
	object.setFloatAttribute('cat_wpn_damage.wpn_attack_speed', 1)
	object.setStringAttribute('cat_wpn_damage.wpn_damage_type', 'Energy')
	object.setStringAttribute('cat_wpn_damage.damage', '600-1300')
	object.setStringAttribute('cat_wpn_damage.wpn_range', '0-5m')
	object.setStringAttribute('cat_wpn_damage.wpn_category', 'Baton')
	object.setIntAttribute('cat_wpn_damage.dps', object.getDamagePerSecond())
	return