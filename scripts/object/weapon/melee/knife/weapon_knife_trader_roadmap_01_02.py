import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_knife_trader_roadmap_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_knife_trader_roadmap_01_02')
	object.setStringAttribute('class_required', 'Trader')
	object.setIntAttribute('required_combat_level', 42)
	object.setFloatAttribute('cat_wpn_damage.wpn_attack_speed', 0)
	object.setStringAttribute('cat_wpn_damage.damage', '80 - 160')
	object.setStringAttribute('cat_wpn_damage.wpn_range', '0-5m')
	object.setStringAttribute('cat_wpn_damage.wpn_category', 'One-Handed Melee')
	object.setIntAttribute('cat_wpn_damage.dps', object.getDamagePerSecond())
	return