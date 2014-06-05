import sys

def getLuck():
	return 30

def getPrecision():
	return 30

def getStrength():
	return 100

def getConstitution():
	return 40

def getStamina():
	return 40

def getAgility():
	return 120

def getHealth():
	return 100

def getAction():
	return 75

def getRewards(name):
	if name == 'item_force_sensitive_ring_02_01':
		return "object/tangible/wearables/ring/shared_ring_s02.iff"

	if name == 'item_npe_fs_robe_02_02':
		return "object/tangible/wearables/robe/shared_robe_jedi_padawan.iff"

	if name == 'weapon_roadmap_lightsaber_02_02':
		return "object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_s1.iff"
		
	if name == 'item_force_sensitive_ring_01_02':
		return "object/tangible/wearables/ring/shared_ring_s01.iff"
		
	if name == 'item_krayt_pearl_04_01':
		return "object/tangible/component/weapon/lightsaber/shared_lightsaber_module_krayt_dragon_pearl.iff"
		
	if name == 'item_force_sensitive_pendant_01_02':
		return "object/tangible/wearables/necklace/shared_necklace_s12.iff"
		
	if name == 'item_force_sensitive_clicky_01_02':
		return "object/tangible/loot/generic_usable/shared_generic_crystal.iff"
		
	if name == 'item_roadmap_belt_force_sensitive_01_02':
		return "object/tangible/wearables/armor/zam/shared_armor_zam_wesell_belt.iff"		