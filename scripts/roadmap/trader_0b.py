import sys

def getLuck():
	return 90

def getPrecision():
	return 140

def getStrength():
	return 140

def getConstitution():
	return 0

def getStamina():
	return 50

def getAgility():
	return 140

def getHealth():
	return 100

def getAction():
	return 75

def getRewards(name):
	if name == 'item_npe_gen_craft_tool_trader_03_01':
		return "object/tangible/crafting/base/shared_generic_base_tool.iff"

	if name == 'item_trader_backpack_01_02':
		return "object/tangible/wearables/backpack/shared_backpack_s04.iff"

	if name == 'weapon_knife_trader_roadmap_01_02':
		return "object/weapon/melee/knife/shared_knife_dagger.iff"
		
	if name == 'item_trader_ring_01_02':
		return "object/tangible/wearables/ring/shared_ring_s02.iff"
	
	# Human Clothing
	if name == 'item_trader_roadmap_jumpsuit_02_01':
		return "object/tangible/wearables/bodysuit/shared_bodysuit_s13.iff"
		
	if name == 'item_trader_roadmap_boots_02_01':
		return "object/tangible/wearables/boots/shared_boots_s03.iff"
		
	if name == 'item_trader_roadmap_gloves_02_01':
		return "object/tangible/wearables/gloves/shared_gloves_s02.iff"
	
	# Wookiee Clothing
	if name == 'item_trader_roadmap_shoulder_pad_02_01':
		return "object/tangible/wearables/wookiee/shared_wke_shoulder_pad_s01.iff"
		
	if name == 'item_trader_roadmap_gloves_02_02':
		return "object/tangible/wearables/wookiee/shared_wke_gloves_s01.iff"
	
	# Ithorian Clothing
	if name == 'item_trader_roadmap_jumpsuit_02_02':
		return "object/tangible/wearables/ithorian/shared_ith_bodysuit_s03.iff"
	
	if name == 'item_trader_roadmap_hat_02_01':
		return "object/tangible/wearables/ithorian/shared_ith_hat_s03.iff"
	
	if name == 'weapon_pistol_trader_roadmap_01_02':
		return "object/weapon/ranged/pistol/shared_pistol_scout_blaster.iff"
		
	if name == 'item_trader_pendant_01_02':
		return "object/tangible/wearables/necklace/shared_necklace_s07.iff"
		
	if name == 'item_trader_clicky_01_02':
		return "object/tangible/crafting/station/shared_generic_generic_tool.iff"
		
	if name == 'item_roadmap_belt_trader_01_02':
		return "object/tangible/wearables/armor/zam/shared_armor_zam_wesell_belt.iff"
	return