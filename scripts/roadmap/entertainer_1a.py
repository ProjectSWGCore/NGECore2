import sys

def getLuck():
	return 80

def getPrecision():
	return 0

def getStrength():
	return 0

def getConstitution():
	return 40

def getStamina():
	return 120

def getAgility():
	return 120

def getHealth():
	return 100

def getAction():
	return 75

def getRewards(name):
	
	if name == 'item_entertainer_backpack_01_02':
		return "object/tangible/wearables/backpack/shared_backpack_s01.iff"

	if name == 'armor_entertainer_roadmap_cloak_02_01':
		return "object/tangible/wearables/robe/shared_robe_s01.iff"

	if name == 'armor_entertainer_roadmap_boots_02_01':
		return "object/tangible/wearables/boots/shared_boots_s03.iff"

	if name == 'armor_entertainer_roadmap_gloves_02_01':
		return "object/tangible/wearables/gloves/shared_gloves_s02.iff"

	if name == 'armor_entertainer_roadmap_pants_02_01':
		return "object/tangible/wearables/pants/shared_pants_s01.iff"	

	if name == 'weapon_vibro_en_roadmap_01_02':
		return "object/weapon/melee/special/shared_vibroknuckler.iff"		

	if name == 'weapon_pistol_en_roadmap_01_02':
		return "object/weapon/ranged/pistol/shared_pistol_striker.iff"
		
	if name == 'weapon_vibro_en_roadmap_02_01':
		return "object/weapon/melee/special/shared_vibroknuckler.iff.iff"

	if name == 'item_entertainer_ring_01_02':
		return "object/tangible/wearables/ring/shared_ring_s02.iff"			

	if name == 'item_entertainer_pendant_01_02':
		return "object/tangible/wearables/necklace/shared_necklace_s02.iff"				

	if name == 'item_entertainer_clicky_01_02':
		return "object/tangible/loot/generic_usable/shared_survey_pad_adv_generic.iff"			

	if name == 'item_roadmap_belt_entertainer_01_02':
		return "object/tangible/wearables/armor/zam/shared_armor_zam_wesell_belt.iff"	
	
