import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_lightningbeam_commando_roadmap_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_lightningbeam_commando_roadmap_01_02')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 5)
	object.setStringAttribute('class_required', 'Commando')
	object.setIntAttribute('required_combat_level', 30)
	object.setFloatAttribute('cat_wpn_damage.wpn_attack_speed', 0.8)
	object.setStringAttribute('cat_wpn_damage.wpn_damage_type', 'Energy')
	object.setStringAttribute('cat_wpn_damage.damage', '261-522')
	object.setStringAttribute('cat_wpn_damage.wpn_range', '0-64m')
	object.setStringAttribute('cat_wpn_damage.wpn_category', 'Rifle')
	object.setIntAttribute('cat_wpn_damage.dps', object.getDamagePerSecond())
	return