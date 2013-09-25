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
	
	heroism3 = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s03_l.iff', object.getPlanet())
	heroism3.setStfFilename('static_item_n')
	heroism3.setStfName('item_bracelet_l_set_hero_01_01')
	heroism3.setDetailFilename('static_item_d')
	heroism3.setDetailName('item_bracelet_l_set_hero_01_01')
	heroism3.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_hero_1')
	heroism3.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_hero_2')
	heroism3.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_hero_3')
	heroism3.setIntAttribute('cat_stat_mod_bonus.food_luck_modified' , 30)
	heroism3.setIntAttribute('cat_stat_mod_bonus.food_precision_modified' , 30)
	heroism3.setIntAttribute('cat_stat_mod_bonus.food_strength_modified' , 30)
	

	inventory = object.getSlottedObject('inventory')
	if not inventory:
		return
	inventory.add(heroism3)
	
	heroism4 = core.objectService.createObject('object/tangible/wearables/ring/shared_ring_s04.iff', object.getPlanet())
	heroism4.setStfFilename('static_item_n')
	heroism4.setStfName('item_ring_set_hero_01_01')
	heroism4.setDetailFilename('static_item_d')
	heroism4.setDetailName('item_ring_set_hero_01_01')
	heroism4.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_hero_1')
	heroism4.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_hero_2')
	heroism4.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_hero_3')
	heroism4.setIntAttribute('cat_stat_mod_bonus.food_luck_modified' , 30)
	heroism4.setIntAttribute('cat_stat_mod_bonus.food_precision_modified' , 30)
	heroism4.setIntAttribute('cat_stat_mod_bonus.food_strength_modified' , 30)
	

	inventory = object.getSlottedObject('inventory')
	if not inventory:
		return
	inventory.add(heroism4)
	
	heroism5 = core.objectService.createObject('object/tangible/wearables/ring/shared_ring_s02.iff', object.getPlanet())
	heroism5.setStfFilename('static_item_n')
	heroism5.setStfName('item_ring_a_set_hero')
	heroism5.setDetailFilename('static_item_d')
	heroism5.setDetailName('item_ring_a_set_hero')
	heroism5.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_hero_1')
	heroism5.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_hero_2')
	heroism5.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_hero_3')
	heroism5.setIntAttribute('cat_stat_mod_bonus.food_luck_modified' , 30)
	heroism5.setIntAttribute('cat_stat_mod_bonus.food_precision_modified' , 30)
	heroism5.setIntAttribute('cat_stat_mod_bonus.food_strength_modified' , 30)
	

	inventory = object.getSlottedObject('inventory')
	if not inventory:
		return
	inventory.add(heroism5)
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
	elif profession == 'commando_1a':
		object.addAbility('co_del_ae_cc_2_2')
		object.addAbility('co_del_ae_dm_3')
		object.addAbility('co_del_ae_cc_1_3')
		object.addAbility('co_enrage_1')
		
		frontman1 = core.objectService.createObject('object/tangible/wearables/necklace/shared_necklace_s11.iff', object.getPlanet())
		frontman1.setStfFilename('static_item_n')
		frontman1.setStfName('item_necklace_set_commando_utility_a_01_01')
		frontman1.setDetailFilename('static_item_d')
		frontman1.setDetailName('item_necklace_set_commando_utility_a_01_01')
		frontman1.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_commando_utility_a_1')
		frontman1.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_utility_a_2')
		frontman1.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_utility_a_3')
		frontman1.setIntAttribute('cat_stat_mod_bonus.food_constitution_modified' , 20)
		frontman1.setIntAttribute('cat_skill_mod_bonus.@stat_n:combat_block_chance' , 2)
		frontman1.setIntAttribute('cat_skill_mod_bonus.@stat_n:combat_block_value' , 50)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(frontman1)
		
		frontman2 = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s02_r.iff', object.getPlanet())
		frontman2.setStfFilename('static_item_n')
		frontman2.setStfName('item_bracelet_r_set_commando_utility_a_01_01')
		frontman2.setDetailFilename('static_item_d')
		frontman2.setDetailName('item_bracelet_r_set_commando_utility_a_01_01')
		frontman2.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_commando_utility_a_1')
		frontman2.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_utility_a_2')
		frontman2.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_utility_a_3')
		frontman2.setIntAttribute('cat_stat_mod_bonus.food_constitution_modified' , 20)
		frontman2.setIntAttribute('cat_skill_mod_bonus.@stat_n:combat_block_chance' , 2)
		frontman2.setIntAttribute('cat_skill_mod_bonus.@stat_n:combat_block_value' , 50)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(frontman2)
		
		frontman3 = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s02_l.iff', object.getPlanet())
		frontman3.setStfFilename('static_item_n')
		frontman3.setStfName('item_bracelet_l_set_commando_utility_a_01_01')
		frontman3.setDetailFilename('static_item_d')
		frontman3.setDetailName('item_bracelet_l_set_commando_utility_a_01_01')
		frontman3.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_commando_utility_a_1')
		frontman3.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_utility_a_2')
		frontman3.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_utility_a_3')
		frontman3.setIntAttribute('cat_stat_mod_bonus.food_constitution_modified' , 20)
		frontman3.setIntAttribute('cat_skill_mod_bonus.@stat_n:combat_block_chance' , 2)
		frontman3.setIntAttribute('cat_skill_mod_bonus.@stat_n:combat_block_value' , 50)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(frontman3)
		
		frontman4 = core.objectService.createObject('object/tangible/wearables/ring/shared_ring_s01.iff', object.getPlanet())
		frontman4.setStfFilename('static_item_n')
		frontman4.setStfName('item_ring_set_commando_utility_a_01_01')
		frontman4.setDetailFilename('static_item_d')
		frontman4.setDetailName('item_ring_set_commando_utility_a_01_01')
		frontman4.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_commando_utility_a_1')
		frontman4.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_utility_a_2')
		frontman4.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_utility_a_3')
		frontman4.setIntAttribute('cat_stat_mod_bonus.food_constitution_modified' , 20)
		frontman4.setIntAttribute('cat_skill_mod_bonus.@stat_n:combat_block_chance' , 2)
		frontman4.setIntAttribute('cat_skill_mod_bonus.@stat_n:combat_block_value' , 50)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(frontman4)
		
		frontman5 = core.objectService.createObject('object/tangible/wearables/ring/shared_ring_s03.iff', object.getPlanet())
		frontman5.setStfFilename('static_item_n')
		frontman5.setStfName('item_ring_a_set_commando_utility_a')
		frontman5.setDetailFilename('static_item_d')
		frontman5.setDetailName('item_ring_a_set_commando_utility_a')
		frontman5.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_commando_utility_a_1')
		frontman5.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_utility_a_2')
		frontman5.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_utility_a_3')
		frontman5.setIntAttribute('cat_stat_mod_bonus.food_constitution_modified' , 20)
		frontman5.setIntAttribute('cat_skill_mod_bonus.@stat_n:combat_block_chance' , 2)
		frontman5.setIntAttribute('cat_skill_mod_bonus.@stat_n:combat_block_value' , 50)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(frontman5)
		
		grenadier1 = core.objectService.createObject('object/tangible/wearables/necklace/shared_necklace_s10.iff', object.getPlanet())
		grenadier1.setStfFilename('static_item_n')
		grenadier1.setStfName('item_necklace_set_commando_dps_01_01')
		grenadier1.setDetailFilename('static_item_d')
		grenadier1.setDetailName('item_necklace_set_commando_dps_01_01')
		grenadier1.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_commando_dps_1')
		grenadier1.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_dps_2')
		grenadier1.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_dps_3')
		grenadier1.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_co_grenade' , 3)
		grenadier1.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_co_grenade' , 2)
		grenadier1.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_freeshot_co_grenade' , 2)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(grenadier1)
		
		grenadier2 = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s06_r.iff', object.getPlanet())
		grenadier2.setStfFilename('static_item_n')
		grenadier2.setStfName('item_bracelet_r_set_commando_dps_01_01')
		grenadier2.setDetailFilename('static_item_d')
		grenadier2.setDetailName('item_bracelet_r_set_commando_dps_01_01')
		grenadier2.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_commando_dps_1')
		grenadier2.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_dps_2')
		grenadier2.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_dps_3')
		grenadier2.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_co_grenade' , 3)
		grenadier2.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_co_grenade' , 2)
		grenadier2.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_freeshot_co_grenade' , 2)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(grenadier2)
		
		grenadier3 = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s06_l.iff', object.getPlanet())
		grenadier3.setStfFilename('static_item_n')
		grenadier3.setStfName('item_bracelet_l_set_commando_dps_01_01')
		grenadier3.setDetailFilename('static_item_d')
		grenadier3.setDetailName('item_bracelet_l_set_commando_dps_01_01')
		grenadier3.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_commando_dps_1')
		grenadier3.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_dps_2')
		grenadier3.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_dps_3')
		grenadier3.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_co_grenade' , 3)
		grenadier3.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_co_grenade' , 2)
		grenadier3.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_freeshot_co_grenade' , 2)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(grenadier3)
		
		grenadier4 = core.objectService.createObject('object/tangible/wearables/ring/shared_ring_s02.iff', object.getPlanet())
		grenadier4.setStfFilename('static_item_n')
		grenadier4.setStfName('item_ring_set_commando_dps_01_01')
		grenadier4.setDetailFilename('static_item_d')
		grenadier4.setDetailName('item_ring_set_commando_dps_01_01')
		grenadier4.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_commando_dps_1')
		grenadier4.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_dps_2')
		grenadier4.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_dps_3')
		grenadier4.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_co_grenade' , 3)
		grenadier4.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_co_grenade' , 2)
		grenadier4.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_freeshot_co_grenade' , 2)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(grenadier4)
		
		grenadier5 = core.objectService.createObject('object/tangible/wearables/ring/shared_ring_s04.iff', object.getPlanet())
		grenadier5.setStfFilename('static_item_n')
		grenadier5.setStfName('item_ring_a_set_commando_dps')
		grenadier5.setDetailFilename('static_item_d')
		grenadier5.setDetailName('item_ring_a_set_commando_dps')
		grenadier5.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_commando_dps_1')
		grenadier5.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_dps_2')
		grenadier5.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_dps_3')
		grenadier5.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_co_grenade' , 3)
		grenadier5.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_co_grenade' , 2)
		grenadier5.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_freeshot_co_grenade' , 2)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(grenadier5)	
		
		juggernaut1 = core.objectService.createObject('object/tangible/wearables/necklace/shared_necklace_s12.iff', object.getPlanet())
		juggernaut1.setStfFilename('static_item_n')
		juggernaut1.setStfName('item_necklace_set_commando_utility_b_01_01')
		juggernaut1.setDetailFilename('static_item_d')
		juggernaut1.setDetailName('item_necklace_set_commando_utility_b_01_01')
		juggernaut1.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_commando_utility_b_1')
		juggernaut1.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_utility_b_2')
		juggernaut1.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_utility_b_3')
		juggernaut1.setIntAttribute('cat_stat_mod_bonus.food_strength_modified' , 15)
		juggernaut1.setIntAttribute('cat_stat_mod_bonus.food_constitution_modified' , 10)
		juggernaut1.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_devastation_bonus' , 5)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(juggernaut1)
		
		juggernaut2 = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s03_r.iff', object.getPlanet())
		juggernaut2.setStfFilename('static_item_n')
		juggernaut2.setStfName('item_bracelet_r_set_commando_utility_b_01_01')
		juggernaut2.setDetailFilename('static_item_d')
		juggernaut2.setDetailName('item__bracelet_r_set_commando_utility_b_01_01')
		juggernaut2.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_commando_utility_b_1')
		juggernaut2.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_utility_b_2')
		juggernaut2.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_utility_b_3')
		juggernaut2.setIntAttribute('cat_stat_mod_bonus.food_strength_modified' , 15)
		juggernaut2.setIntAttribute('cat_stat_mod_bonus.food_constitution_modified' , 10)
		juggernaut2.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_devastation_bonus' , 5)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(juggernaut2)
		
		juggernaut3 = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s03_l.iff', object.getPlanet())
		juggernaut3.setStfFilename('static_item_n')
		juggernaut3.setStfName('item_bracelet_l_set_commando_utility_b_01_01')
		juggernaut3.setDetailFilename('static_item_d')
		juggernaut3.setDetailName('item__bracelet_l_set_commando_utility_b_01_01')
		juggernaut3.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_commando_utility_b_1')
		juggernaut3.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_utility_b_2')
		juggernaut3.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_utility_b_3')
		juggernaut3.setIntAttribute('cat_stat_mod_bonus.food_strength_modified' , 15)
		juggernaut3.setIntAttribute('cat_stat_mod_bonus.food_constitution_modified' , 10)
		juggernaut3.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_devastation_bonus' , 5)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(juggernaut3)
		
		juggernaut4 = core.objectService.createObject('object/tangible/wearables/ring/shared_ring_s02.iff', object.getPlanet())
		juggernaut4.setStfFilename('static_item_n')
		juggernaut4.setStfName('item_ring_set_commando_utility_b_01_01')
		juggernaut4.setDetailFilename('static_item_d')
		juggernaut4.setDetailName('item__ring_set_commando_utility_b_01_01')
		juggernaut4.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_commando_utility_b_1')
		juggernaut4.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_utility_b_2')
		juggernaut4.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_utility_b_3')
		juggernaut4.setIntAttribute('cat_stat_mod_bonus.food_strength_modified' , 15)
		juggernaut4.setIntAttribute('cat_stat_mod_bonus.food_constitution_modified' , 10)
		juggernaut4.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_devastation_bonus' , 5)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(juggernaut4)
		
		juggernaut5 = core.objectService.createObject('object/tangible/wearables/ring/shared_ring_s04.iff', object.getPlanet())
		juggernaut5.setStfFilename('static_item_n')
		juggernaut5.setStfName('item_ring_a_set_commando_utility_b')
		juggernaut5.setDetailFilename('static_item_d')
		juggernaut5.setDetailName('item__ring_a_set_commando_utility_b')
		juggernaut5.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_commando_utility_b_1')
		juggernaut5.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_utility_b_2')
		juggernaut5.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_utility_b_3')
		juggernaut5.setIntAttribute('cat_stat_mod_bonus.food_strength_modified' , 15)
		juggernaut5.setIntAttribute('cat_stat_mod_bonus.food_constitution_modified' , 10)
		juggernaut5.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_devastation_bonus' , 5)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(juggernaut5)
		return
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
		
		direfate1 = core.objectService.createObject('object/tangible/wearables/necklace/shared_necklace_s03.iff', object.getPlanet())
		direfate1.setStfFilename('static_item_n')
		direfate1.setStfName('item_necklace_set_bh_utility_b_01_01')
		direfate1.setDetailFilename('static_item_d')
		direfate1.setDetailName('item_necklace_set_bh_utility_b_01_01')
		direfate1.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_bh_utility_b_1')
		direfate1.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_utility_b_2')
		direfate1.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_utility_b_3')
		direfate1.setIntAttribute('cat_skill_mod_bonus.@stat_n:bh_dire_root' , 1)
		direfate1.setIntAttribute('cat_skill_mod_bonus.@stat_n:bh_dire_snare' , 1)
		direfate1.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_dm_cc' , 2)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(direfate1)
		
		direfate2 = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s04_r.iff', object.getPlanet())
		direfate2.setStfFilename('static_item_n')
		direfate2.setStfName('item_bracelet_r_set_bh_utility_b_01_01')
		direfate2.setDetailFilename('static_item_d')
		direfate2.setDetailName('item_bracelet_r_set_bh_utility_b_01_01')
		direfate2.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_bh_utility_b_1')
		direfate2.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_utility_b_2')
		direfate2.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_utility_b_3')
		direfate2.setIntAttribute('cat_skill_mod_bonus.@stat_n:bh_dire_root' , 1)
		direfate2.setIntAttribute('cat_skill_mod_bonus.@stat_n:bh_dire_snare' , 1)
		direfate2.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_dm_cc' , 2)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(direfate2)
		
		direfate3 = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s04_l.iff', object.getPlanet())
		direfate3.setStfFilename('static_item_n')
		direfate3.setStfName('item_bracelet_l_set_bh_utility_b_01_01')
		direfate3.setDetailFilename('static_item_d')
		direfate3.setDetailName('item_bracelet_l_set_bh_utility_b_01_01')
		direfate3.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_bh_utility_b_1')
		direfate3.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_utility_b_2')
		direfate3.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_utility_b_3')
		direfate3.setIntAttribute('cat_skill_mod_bonus.@stat_n:bh_dire_root' , 1)
		direfate3.setIntAttribute('cat_skill_mod_bonus.@stat_n:bh_dire_snare' , 1)
		direfate3.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_dm_cc' , 2)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(direfate3)
		
		direfate4 = core.objectService.createObject('object/tangible/wearables/ring/shared_ring_s01.iff', object.getPlanet())
		direfate4.setStfFilename('static_item_n')
		direfate4.setStfName('item_ring_set_bh_utility_b_01_01')
		direfate4.setDetailFilename('static_item_d')
		direfate4.setDetailName('item_ring_set_bh_utility_b_01_01')
		direfate4.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_bh_utility_b_1')
		direfate4.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_utility_b_2')
		direfate4.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_utility_b_3')
		direfate4.setIntAttribute('cat_skill_mod_bonus.@stat_n:bh_dire_root' , 1)
		direfate4.setIntAttribute('cat_skill_mod_bonus.@stat_n:bh_dire_snare' , 1)
		direfate4.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_dm_cc' , 2)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(direfate4)
		
		direfate5 = core.objectService.createObject('object/tangible/wearables/ring/shared_ring_s03.iff', object.getPlanet())
		direfate5.setStfFilename('static_item_n')
		direfate5.setStfName('item_ring_a_set_bh_utility_b')
		direfate5.setDetailFilename('static_item_d')
		direfate5.setDetailName('item_ring_a_set_bh_utility_b')
		direfate5.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_bh_utility_b_1')
		direfate5.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_utility_b_2')
		direfate5.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_utility_b_3')
		direfate5.setIntAttribute('cat_skill_mod_bonus.@stat_n:bh_dire_root' , 1)
		direfate5.setIntAttribute('cat_skill_mod_bonus.@stat_n:bh_dire_snare' , 1)
		direfate5.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_dm_cc' , 2)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(direfate5)
		
		flawless1 = core.objectService.createObject('object/tangible/wearables/necklace/shared_necklace_s02.iff', object.getPlanet())
		flawless1.setStfFilename('static_item_n')
		flawless1.setStfName('item_necklace_set_bh_utility_a_01_01')
		flawless1.setDetailFilename('static_item_d')
		flawless1.setDetailName('item_necklace_set_bh_utility_a_01_01')
		flawless1.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_bh_utility_a_1')
		flawless1.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_utility_a_2')
		flawless1.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_utility_a_3')
		flawless1.setIntAttribute('cat_stat_mod_bonus.food_luck_modified' , 30)
		flawless1.setIntAttribute('cat_stat_mod_bonus.food_precision_modified' , 50)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(flawless1)
		
		flawless2 = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s03_r.iff', object.getPlanet())
		flawless2.setStfFilename('static_item_n')
		flawless2.setStfName('item_bracelet_r_set_bh_utility_a_01_01')
		flawless2.setDetailFilename('static_item_d')
		flawless2.setDetailName('item_bracelet_r_set_bh_utility_a_01_01')
		flawless2.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_bh_utility_b_1')
		flawless2.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_utility_b_2')
		flawless2.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_utility_b_3')
		flawless2.setIntAttribute('cat_stat_mod_bonus.food_luck_modified' , 30)
		flawless2.setIntAttribute('cat_stat_mod_bonus.food_precision_modified' , 50)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(flawless2)
		
		flawless3 = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s03_l.iff', object.getPlanet())
		flawless3.setStfFilename('static_item_n')
		flawless3.setStfName('item_bracelet_l_set_bh_utility_a_01_01')
		flawless3.setDetailFilename('static_item_d')
		flawless3.setDetailName('item_bracelet_l_set_bh_utility_a_01_01')
		flawless3.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_bh_utility_b_1')
		flawless3.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_utility_b_2')
		flawless3.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_utility_b_3')
		flawless3.setIntAttribute('cat_stat_mod_bonus.food_luck_modified' , 30)
		flawless3.setIntAttribute('cat_stat_mod_bonus.food_precision_modified' , 50)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(flawless3)
		
		flawless4 = core.objectService.createObject('object/tangible/wearables/ring/shared_ring_s02.iff', object.getPlanet())
		flawless4.setStfFilename('static_item_n')
		flawless4.setStfName('item_ring_set_bh_utility_a_01_01')
		flawless4.setDetailFilename('static_item_d')
		flawless4.setDetailName('item_ring_set_bh_utility_a_01_01')
		flawless4.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_bh_utility_b_1')
		flawless4.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_utility_b_2')
		flawless4.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_utility_b_3')
		flawless4.setIntAttribute('cat_stat_mod_bonus.food_luck_modified' , 30)
		flawless4.setIntAttribute('cat_stat_mod_bonus.food_precision_modified' , 50)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(flawless4)
		
		flawless5 = core.objectService.createObject('object/tangible/wearables/ring/shared_ring_s04.iff', object.getPlanet())
		flawless5.setStfFilename('static_item_n')
		flawless5.setStfName('item_ring_a_set_bh_utility_a')
		flawless5.setDetailFilename('static_item_d')
		flawless5.setDetailName('item_ring_a_set_bh_utility_a')
		flawless5.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_bh_utility_b_1')
		flawless5.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_utility_b_2')
		flawless5.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_utility_b_3')
		flawless5.setIntAttribute('cat_stat_mod_bonus.food_luck_modified' , 30)
		flawless5.setIntAttribute('cat_stat_mod_bonus.food_precision_modified' , 50)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(flawless5)
				
		enforcer1 = core.objectService.createObject('object/tangible/wearables/necklace/shared_necklace_s01.iff', object.getPlanet())
		enforcer1.setStfFilename('static_item_n')
		enforcer1.setStfName('item_necklace_set_bh_dps_01_01')
		enforcer1.setDetailFilename('static_item_d')
		enforcer1.setDetailName('item_necklace_set_bh_dps_01_01')
		enforcer1.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_bh_dps_1')
		enforcer1.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_dps_2')
		enforcer1.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_dps_3')
		enforcer1.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_dm' , 1)
		enforcer1.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_dm_crit' , 1)
		enforcer1.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_buff_duration_line_bh_return_fire' , 1)
		enforcer1.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_cooldown_line_dm' , 1)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(enforcer1)
		
		enforcer2 = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s02_r.iff', object.getPlanet())
		enforcer2.setStfFilename('static_item_n')
		enforcer2.setStfName('item_bracelet_r_set_bh_dps_01_01')
		enforcer2.setDetailFilename('static_item_d')
		enforcer2.setDetailName('item_bracelet_r_set_dps_01_01')
		enforcer2.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_bh_dps_1')
		enforcer2.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_dps_2')
		enforcer2.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_dps_3')
		enforcer2.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_dm' , 1)
		enforcer2.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_dm_crit' , 1)
		enforcer2.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_buff_duration_line_bh_return_fire' , 1)
		enforcer2.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_cooldown_line_dm' , 1)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(enforcer2)
		
		enforcer3 = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s02_l.iff', object.getPlanet())
		enforcer3.setStfFilename('static_item_n')
		enforcer3.setStfName('item_bracelet_l_set_bh_dps_01_01')
		enforcer3.setDetailFilename('static_item_d')
		enforcer3.setDetailName('item_bracelet_l_set_bh_dps_01_01')
		enforcer3.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_bh_dps_1')
		enforcer3.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_dps_2')
		enforcer3.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_dps_3')
		enforcer3.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_dm' , 1)
		enforcer3.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_dm_crit' , 1)
		enforcer3.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_buff_duration_line_bh_return_fire' , 1)
		enforcer3.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_cooldown_line_dm' , 1)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(enforcer3)
		
		enforcer4 = core.objectService.createObject('object/tangible/wearables/ring/shared_ring_s01.iff', object.getPlanet())
		enforcer4.setStfFilename('static_item_n')
		enforcer4.setStfName('item_ring_set_bh_dps_01_01')
		enforcer4.setDetailFilename('static_item_d')
		enforcer4.setDetailName('item_ring_set_bh_dps_01_01')
		enforcer4.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_bh_dps_1')
		enforcer4.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_dps_2')
		enforcer4.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_dps_3')
		enforcer4.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_dm' , 1)
		enforcer4.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_dm_crit' , 1)
		enforcer4.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_buff_duration_line_bh_return_fire' , 1)
		enforcer4.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_cooldown_line_dm' , 1)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(enforcer4)
		
		enforcer5 = core.objectService.createObject('object/tangible/wearables/ring/shared_ring_s03.iff', object.getPlanet())
		enforcer5.setStfFilename('static_item_n')
		enforcer5.setStfName('item_ring_a_set_bh_dps')
		enforcer5.setDetailFilename('static_item_d')
		enforcer5.setDetailName('item_ring_a_set_bh_dps')
		enforcer5.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_bh_dps_1')
		enforcer5.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_dps_2')
		enforcer5.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_dps_3')
		enforcer5.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_dm' , 1)
		enforcer5.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_dm_crit' , 1)
		enforcer5.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_buff_duration_line_bh_return_fire' , 1)
		enforcer5.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_cooldown_line_dm' , 1)
	
		inventory = object.getSlottedObject('inventory')
		if not inventory:
			return
		inventory.add(enforcer5)		
		return
	return
	
		
	return
