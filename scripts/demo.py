import sys

def CreateStartingCharacter(core, object):
	
	testObject = core.objectService.createObject('object/weapon/ranged/rifle/shared_rifle_t21.iff', object.getPlanet())
	testObject.setCustomName('This is a Jython Rifle')
	testObject.setStringAttribute('crafter', 'Light')
	testObject.setStringAttribute('cat_wpn_damage.wpn_damage_type', '@obj_attr_n:armor_eff_energy')
	testObject.setIntAttribute('cat_wpn_damage.wpn_damage_min', 425)
	testObject.setIntAttribute('cat_wpn_damage.wpn_damage_max', 1140)
	object.addSkillMod('constitution_modified' , 350)
	object.addSkillMod('strength_modified' , 350)
	object.addSkillMod('precision_modified' , 350)
	object.addSkillMod('luck_modified' , 350)
	object.addSkillMod('agility_modified' , 350)
	object.addSkillMod('stamina_modified' , 350)
	object.addSkillMod('kinetic' , 10000)
	object.addSkillMod('energy' , 10000)
	object.addSkillMod('heat' , 6000)
	object.addSkillMod('cold' , 6000)
	object.addSkillMod('acid' , 6000)
	object.addSkillMod('electricity' , 6000)
	object.addSkillMod('combat_strikethrough_value' , 50)
	object.addSkillMod('display_only_dodge' , 2000)
	object.addSkillMod('display_only_parry' , 1000)
	object.addSkillMod('display_only_strikethrough' , 500)
	object.addSkillMod('display_only_critical' , 2500)
	object.addSkillMod('display_only_evasion' , 1000)
	object.addSkillMod('display_only_glancing_blow' , 750)
	object.addSkillMod('display_only_block' , 2000)
	object.addSkillMod('combat_block_value' , 0)

	inventory = object.getSlottedObject('inventory')
	if not inventory:
		return
	inventory.add(testObject)
	
	testClothing = core.objectService.createObject('object/tangible/wearables/cape/shared_cape_rebel_01.iff', object.getPlanet())
	testClothing.setCustomName('Test Cape')
	testCloak = core.objectService.createObject('object/tangible/wearables/robe/shared_robe_jedi_dark_s05.iff', object.getPlanet())
	testCloak.setCustomName('Test Cloak')

	inventory.add(testClothing)
	inventory.add(testCloak)
	profession = object.getSlottedObject('ghost').getProfession()
	addProfessionAbilities(core, object, profession)
	
	heroism1 = core.objectService.createObject('object/tangible/wearables/necklace/shared_necklace_s10.iff', object.getPlanet())
	heroism1.setStfFilename('static_item_n')
	heroism1.setStfName('item_necklace_set_hero_01_01')
	heroism1.setDetailFilename('static_item_d')
	heroism1.setDetailName('item_necklace_set_hero_01_01')
	heroism1.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_hero_1')
	heroism1.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_hero_2')
	heroism1.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_hero_3')
	heroism1.setIntAttribute('cat_stat_mod_bonus.food_luck_modified' , 30)
	heroism1.setIntAttribute('cat_stat_mod_bonus.food_precision_modified' , 30)
	heroism1.setIntAttribute('cat_stat_mod_bonus.food_strength_modified' , 30)
	

	inventory = object.getSlottedObject('inventory')
	if not inventory:
		return
	inventory.add(heroism1)
	
	heroism2 = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s03_r.iff', object.getPlanet())
	heroism2.setStfFilename('static_item_n')
	heroism2.setStfName('item_bracelet_r_set_hero_01_01')
	heroism2.setDetailFilename('static_item_d')
	heroism2.setDetailName('item_bracelet_r_set_hero_01_01')
	heroism2.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_hero_1')
	heroism2.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_hero_2')
	heroism2.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_hero_3')
	heroism2.setIntAttribute('cat_stat_mod_bonus.food_luck_modified' , 30)
	heroism2.setIntAttribute('cat_stat_mod_bonus.food_precision_modified' , 30)
	heroism2.setIntAttribute('cat_stat_mod_bonus.food_strength_modified' , 30)
	

	inventory = object.getSlottedObject('inventory')
	if not inventory:
		return
	inventory.add(heroism2)
	return
	
def addProfessionAbilities(core, object, profession):

	if profession == 'force_sensitive_1a':
		testObject = core.objectService.createObject('object/weapon/melee/2h_sword/crafted_saber/shared_sword_lightsaber_two_handed_gcw_s01_gen5.iff', object.getPlanet())
		testObject.setCustomName('Lightsaber')
		testObject.setStringAttribute('crafter', 'Light')
		testObject.setStringAttribute('cat_wpn_damage.wpn_damage_type', '@obj_attr_n:armor_eff_energy')
		testObject.setIntAttribute('cat_wpn_damage.wpn_damage_min', 600)
		testObject.setIntAttribute('cat_wpn_damage.wpn_damage_max', 1300)
		inventory = object.getSlottedObject('inventory')
		inventory.add(testObject)

		object.addAbility('fs_sweep_7')
		object.addAbility('fs_drain_7')
		object.addAbility('forceRun')
		object.addAbility('fs_dm_cc_crit_5')
		object.addAbility('fs_maelstrom_5')
		object.addAbility('fs_ae_dm_cc_6')
		object.addAbility('fs_sh_3')
		object.addAbility('fs_dm_cc_6')
		object.addAbility('fs_dm_7')
		object.addAbility('fs_buff_def_1_1')
		object.addAbility('fs_buff_ca_1')
	elif profession == 'medic_1a':
		object.addAbility('me_bacta_bomb_5')
		object.addAbility('me_bacta_grenade_5')
		object.addAbility('me_bacta_ampule_6')
		object.addAbility('me_ae_heal_6')
		object.addAbility('me_dm_8')
		object.addAbility('me_dm_dot_6')
		object.addAbility('me_drag_1')
		object.addAbility('me_rv_pvp_single')
		object.addAbility('me_rv_area')
		object.addAbility('me_rv_pvp_area')
		object.addAbility('me_reckless_stimulation_6')
		object.addAbility('me_sh_1')
		object.addAbility('me_stasis_1')
		object.addAbility('me_stasis_self_1')
		object.addAbility('me_thyroid_rupture_1')
		object.addAbility('me_traumatize_5')
		object.addAbility('me_induce_insanity_1')
	elif profession == 'bounty_hunter_1a':
		object.addAbility('bh_shields_1')
		object.addAbility('bh_dm_8')
		object.addAbility('bh_sh_3')
		object.addAbility('bh_armor_sprint_1')
		object.addAbility('bh_prescience')
		object.addAbility('bh_dm_crit_8')
		object.addAbility('crippleShot')
		object.addAbility('bh_fumble_6')
		object.addAbility('bh_intimidate_6')
		object.addAbility('bh_flawless_strike')
		
	return
