import sys

def getLuck():
	return 80

def getPrecision():
	return 50

def getStrength():
	return 50

def getConstitution():
	return 60

def getStamina():
	return 120

def getAgility():
	return 0

def getHealth():
	return 100

def getAction():
	return 75

def getRewards(name):
	if name == 'item_medic_backpack_01_02':
		return "object/tangible/wearables/backpack/shared_backpack_s03.iff"
	
	if name == 'armor_medic_roadmap_bicep_l_02_01':
		return "object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_bicep_l.iff"
		
	if name == 'armor_medic_roadmap_bicep_r_02_01':
		return "object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_bicep_r.iff"
		
	if name == 'armor_medic_roadmap_bracer_l_02_01':
		return "object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_bracer_l.iff"
		
	if name == 'armor_medic_roadmap_bracer_r_02_01':
		return "object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_bracer_r.iff"	
		
	if name == 'armor_medic_roadmap_boots_02_01':
		return "object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_boots.iff"	
		
	if name == 'armor_medic_roadmap_chest_02_01':
		return "object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_chest_plate.iff"			

	if name == 'armor_medic_roadmap_gloves_02_01':
		return "object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_gloves.iff"
		
	if name == 'armor_medic_roadmap_helmet_02_01':
		return "object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_helmet.iff"				
		
	if name == 'armor_medic_roadmap_leggings_02_01':
		return "object/tangible/wearables/armor/tantel/shared_armor_tantel_skreej_leggings.iff"
		
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
		
	if name == 'weapon_carbine_me_roadmap_01_02':
		return "object/weapon/ranged/pistol/shared_pistol_scout_blaster.iff"
		
	if name == 'item_medic_ring_01_02':
		return "object/tangible/wearables/ring/shared_ring_s02.iff"	
		
	if name == 'weapon_rifle_me_roadmap_01_02':
		return "object/weapon/ranged/carbine/shared_carbine_laser.iff"		
		
	if name == 'item_medic_pendant_01_02':
		return "object/tangible/wearables/necklace/shared_necklace_s02.iff"				
		
	if name == 'item_medic_clicky_01_02':
		return "object/tangible/loot/generic_usable/shared_survey_pad_adv_generic.iff"			

	if name == 'item_roadmap_belt_medic_01_02':
		return "object/tangible/wearables/armor/zam/shared_armor_zam_wesell_belt.iff"
