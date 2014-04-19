import sys

def getLuck():
	return 60

def getPrecision():
	return 120

def getStrength():
	return 120

def getConstitution():
	return 60

def getStamina():
	return 0

def getAgility():
	return 0

def getHealth():
	return 100

def getAction():
	return 75

def getRewards(name):
	if name == 'item_bounty_hunter_backpack_01_02':
		return "object/tangible/wearables/backpack/shared_backpack_s04.iff"
	
	if name == 'armor_commando_roadmap_bicep_l_02_01':
		return "object/tangible/wearables/armor/composite/shared_armor_composite_bicep_l.iff"
		
	if name == 'armor_commando_roadmap_bicep_r_02_01':
		return "object/tangible/wearables/armor/composite/shared_armor_composite_bicep_r.iff"
		
	if name == 'armor_commando_roadmap_bracer_l_02_01':
		return "object/tangible/wearables/armor/composite/shared_armor_composite_bracer_l.iff"
		
	if name == 'armor_commando_roadmap_bracer_r_02_01':
		return "object/tangible/wearables/armor/composite/shared_armor_composite_bracer_r.iff"	
		
	if name == 'armor_commando_roadmap_boots_02_01':
		return "object/tangible/wearables/armor/composite/shared_armor_composite_boots.iff"	
		
	if name == 'armor_commando_roadmap_chest_02_01':
		return "object/tangible/wearables/armor/composite/shared_armor_composite_chest_plate.iff"			

	if name == 'armor_commando_roadmap_gloves_02_01':
		return "object/tangible/wearables/armor/composite/shared_armor_composite_gloves.iff"
		
	if name == 'armor_commando_roadmap_helmet_02_01':
		return "object/tangible/wearables/armor/composite/shared_armor_composite_helmet.iff"				
		
	if name == 'armor_commando_roadmap_leggings_02_01':
		return "object/tangible/wearables/armor/composite/shared_armor_composite_leggings.iff"
		
	if name == 'armor_wookiee_roadmap_chest_02_03':
		return "object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_chestplate.iff"
		
	if name == 'armor_wookiee_roadmap_leggings_02_03':
		return "object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_leggings.iff"
		
	if name == 'armor_wookiee_roadmap_bicep_l_02_03':
		return "object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_bicep_l.iff"	
	
	if name == 'armor_wookiee_roadmap_bicep_r_02_03':
		return "object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_bicep_r.iff"		
		
	if name == 'armor_wookiee_roadmap_bracer_l_02_03':
		return "object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_bracer_l.iff"			
		
	if name == 'armor_wookiee_roadmap_bracer_r_02_03':
		return "object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_bracer_r.iff"

	if name == 'armor_ithorian_roadmap_chest_02_03':
		return "object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_chest_plate.iff"		
		
	if name == 'armor_ithorian_roadmap_leggings_02_03':
		return "object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_leggings.iff"			

	if name == 'armor_ithorian_roadmap_helmet_02_03':
		return "object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_helmet.iff"		

	if name == 'armor_ithorian_roadmap_bicep_l_02_03':
		return "object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_bicep_l.iff"
		
	if name == 'armor_ithorian_roadmap_bicep_r_02_03':
		return "object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_bicep_r.iff"
		
	if name == 'armor_ithorian_roadmap_bracer_l_02_03':
		return "object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_bracer_l.iff"
		
	if name == 'armor_ithorian_roadmap_bracer_r_02_03':
		return "object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_bracer_r.iff"

	if name == 'armor_ithorian_roadmap_boots_02_03':
		return "object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_boots.iff"

	if name == 'armor_ithorian_roadmap_gloves_02_03':
		return "object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_gloves.iff"
		
	if name == 'weapon_lightningbeam_commando_roadmap_01_02':
		return "object/weapon/ranged/rifle/shared_rifle_dlt20.iff"
		
	if name == 'item_commando_ring_01_02':
		return "object/tangible/wearables/ring/shared_ring_s03.iff"
		
	if name == 'weapon_acidbeam_commando_roadmap_01_02':
		return "object/weapon/ranged/carbine/shared_carbine_e11.iff"
		
	if name == 'item_commando_pendant_01_02':
		return "object/tangible/wearables/necklace/shared_necklace_s07.iff"
		
	if name == 'item_commando_clicky_01_02':
		return "object/tangible/loot/generic_usable/shared_survey_pad_adv_generic.iff"

	if name == 'item_roadmap_belt_commando_01_02':
		return "object/tangible/wearables/armor/zam/shared_armor_zam_wesell_belt.iff"