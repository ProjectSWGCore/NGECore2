import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_npe_smuggler_han_solo_gun')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_npe_smuggler_han_solo_gun')
	object.setStringAttribute('class_required', 'Smuggler')
	object.setIntAttribute('required_combat_level', 1)
	object.setFloatAttribute('cat_wpn_damage.wpn_attack_speed', 0.4)
	object.setStringAttribute('cat_wpn_damage.wpn_damage_type', 'Energy')
	object.setStringAttribute('cat_wpn_damage.damage', '13-27')
	object.setStringAttribute('cat_wpn_damage.wpn_range', '0-35m')
	object.setStringAttribute('cat_wpn_damage.wpn_category', 'Pistol')
	object.setIntAttribute('cat_wpn_damage.dps', object.getDamagePerSecond())
	return