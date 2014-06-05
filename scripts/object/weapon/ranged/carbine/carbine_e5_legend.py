import sys

def setup(core, object):
	object.setIntAttribute('required_combat_level', 88)
	object.setFloatAttribute('cat_wpn_damage.wpn_attack_speed', 0.6)
	object.setStringAttribute('cat_wpn_damage.wpn_damage_type', 'Energy')
	object.setStringAttribute('cat_wpn_damage.damage', '400-850')
	object.setStringAttribute('cat_wpn_damage.wpn_range', '0-50m')
	object.setStringAttribute('cat_wpn_damage.wpn_category', 'Carbine')
	object.setIntAttribute('cat_wpn_damage.dps', object.getDamagePerSecond())
	return