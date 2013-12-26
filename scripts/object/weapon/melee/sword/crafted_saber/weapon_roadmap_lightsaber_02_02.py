import sys

def setup(core, object):
	object.setCustomName('First Generation Lightsaber')
	
	object.setFloatAttribute('cat_wpn_damage.wpn_attack_speed', 1)
	object.setStringAttribute('class_required', 'Jedi')
	object.setStringAttribute('cat_wpn_damage.wpn_damage_type', 'Energy')
	object.setStringAttribute('cat_wpn_damage.damage', '200-400')
	object.setStringAttribute('cat_wpn_damage.elemental_value', '200-400')
	object.setIntAttribute('cap_wpn_damage.dps', object.getDamagePerSecond())
	return