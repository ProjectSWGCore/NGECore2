import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_carbine_sp_roadmap_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_carbine_sp_roadmap_01_02')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 5)
	object.setStringAttribute('class_required', 'Spy')
	object.setIntAttribute('required_combat_level', 50)
	object.setFloatAttribute('cat_wpn_damage.wpn_attack_speed', 0.6)
	object.setStringAttribute('cat_wpn_damage.wpn_damage_type', 'Energy')
	object.setStringAttribute('cat_wpn_damage.damage', '195-392')
	object.setStringAttribute('cat_wpn_damage.range', '0-55m')
	object.setStringAttribute('cat_wpn_damage.wpn_category', 'Carbine')
	object.setIntAttribute('cat_wpn_damage.dps', object.getDamagePerSecond())
	return
