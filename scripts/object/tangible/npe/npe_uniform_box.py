import sys
from java.util import Vector

def setup(core, object):
	object.setAttachment('radial_filename', 'object/usable')
	return

def use(core, actor, object):
	
	profession = actor.getSlottedObject('ghost').getProfession()
	items = Vector()
	
	if profession =='force_sensitive_1a':
		robe = core.objectService.createObject('object/tangible/wearables/robe/shared_robe_jedi_padawan.iff', actor.getPlanet(), 'item_npe_fs_robe_02_01')
		items.add(robe)
	
	elif profession =='entertainer_1a':
		if "itho" in actor.getTemplate():
			belt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_belt_s02.iff', actor.getPlanet())
			pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s02.iff', actor.getPlanet())
			vest = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_vest_s02.iff', actor.getPlanet())
			items.add(belt)
			items.add(pants)
			items.add(shirt)
			items.add(vest)
		if "wookie" in actor.getTemplate():
			gloves = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_gloves_s02.iff', actor.getPlanet())
			skirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_skirt_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_shirt_s02.iff', actor.getPlanet())
			hood = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_hood_s02.iff', actor.getPlanet())
			items.add(gloves)
			items.add(skirt)
			items.add(shirt)
			items.add(hood)
		else:
			boots = core.objectService.createObject('object/tangible/wearables/boots/shared_boots_s15.iff', actor.getPlanet(), 'item_entertainer_boots_02_01')
			pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s13.iff', actor.getPlanet(), 'item_entertainer_pants_02_01')
			shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_shirt_s04.iff', actor.getPlanet(), 'item_entertainer_shirt_02_01')
			hat = core.objectService.createObject('object/tangible/wearables/hat/shared_hat_s02.iff', actor.getPlanet(), 'item_entertainer_hat_02_01')
			items.add(boots)
			items.add(pants)
			items.add(shirt)
			items.add(hat)
		
	elif profession =='officer_1a': 
		if "itho" in actor.getTemplate():
			belt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_belt_s02.iff', actor.getPlanet())
			pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s02.iff', actor.getPlanet())
			vest = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_vest_s02.iff', actor.getPlanet())
			items.add(belt)
			items.add(pants)
			items.add(shirt)
			items.add(vest)
		if "wookie" in actor.getTemplate():
			gloves = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_gloves_s02.iff', actor.getPlanet())
			skirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_skirt_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_shirt_s02.iff', actor.getPlanet())
			hood = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_hood_s02.iff', actor.getPlanet())
			items.add(gloves)
			items.add(skirt)
			items.add(shirt)
			items.add(hood)
		else:
			boots = core.objectService.createObject('object/tangible/wearables/boots/shared_boots_s22.iff', actor.getPlanet(), 'item_officer_boots_02_01')
			pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s15.iff', actor.getPlanet(), 'item_officer_pants_02_01')
			shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_shirt_s34.iff', actor.getPlanet(), 'item_officer_shirt_02_01')
			belt = core.objectService.createObject('object/tangible/wearables/belt/shared_belt_s11.iff', actor.getPlanet(), 'item_officer_belt_02_01')
			items.add(boots)
			items.add(pants)
			items.add(shirt)
			items.add(belt)
		
	elif profession =='bounty_hunter_1a':
		if "itho" in actor.getTemplate():
			belt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_belt_s02.iff', actor.getPlanet())
			pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s02.iff', actor.getPlanet())
			vest = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_vest_s02.iff', actor.getPlanet())
			items.add(belt)
			items.add(pants)
			items.add(shirt)
			items.add(vest)
		if "wookie" in actor.getTemplate():
			gloves = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_gloves_s02.iff', actor.getPlanet())
			skirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_skirt_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_shirt_s02.iff', actor.getPlanet())
			hood = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_hood_s02.iff', actor.getPlanet())
			items.add(gloves)
			items.add(skirt)
			items.add(shirt)
			items.add(hood)
		else:
			boots = core.objectService.createObject('object/tangible/wearables/boots/shared_boots_s12.iff', actor.getPlanet(), 'item_bounty_hunter_boots_02_01')
			pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s08.iff', actor.getPlanet(), 'item_bounty_hunter_pants_02_01')
			shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_shirt_s42.iff', actor.getPlanet(), 'item_bounty_hunter_shirt_02_01')
			vest = core.objectService.createObject('object/tangible/wearables/vest/shared_vest_s09.iff', actor.getPlanet(), 'item_bounty_hunter_vest_02_01')
			items.add(boots)
			items.add(pants)
			items.add(shirt)
			items.add(vest)
		
	elif profession =='smuggler_1a':
		if "itho" in actor.getTemplate():
			belt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_belt_s02.iff', actor.getPlanet())
			pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s02.iff', actor.getPlanet())
			vest = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_vest_s02.iff', actor.getPlanet())
			items.add(belt)
			items.add(pants)
			items.add(shirt)
			items.add(vest)
		if "wookie" in actor.getTemplate():
			gloves = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_gloves_s02.iff', actor.getPlanet())
			skirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_skirt_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_shirt_s02.iff', actor.getPlanet())
			hood = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_hood_s02.iff', actor.getPlanet())
			items.add(gloves)
			items.add(skirt)
			items.add(shirt)
			items.add(hood)
		else:
			boots = core.objectService.createObject('object/tangible/wearables/boots/shared_boots_s15.iff', actor.getPlanet(), 'item_smuggler_boots_02_01')
			pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s05.iff', actor.getPlanet(), 'item_smuggler_pants_02_01')
			shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_shirt_s16.iff', actor.getPlanet(), 'item_smuggler_shirt_02_01')
			vest = core.objectService.createObject('object/tangible/wearables/vest/shared_vest_s15.iff', actor.getPlanet(), 'item_smuggler_vest_02_01')
			items.add(boots)
			items.add(pants)
			items.add(shirt)
			items.add(vest)
		
	elif profession =='commando_1a':
		if "itho" in actor.getTemplate():
			belt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_belt_s02.iff', actor.getPlanet())
			pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s02.iff', actor.getPlanet())
			vest = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_vest_s02.iff', actor.getPlanet())
			items.add(belt)
			items.add(pants)
			items.add(shirt)
			items.add(vest)
		if "wookie" in actor.getTemplate():
			gloves = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_gloves_s02.iff', actor.getPlanet())
			skirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_skirt_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_shirt_s02.iff', actor.getPlanet())
			hood = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_hood_s02.iff', actor.getPlanet())
			items.add(gloves)
			items.add(skirt)
			items.add(shirt)
			items.add(hood)
		else:
			boots = core.objectService.createObject('object/tangible/wearables/boots/shared_boots_s22.iff', actor.getPlanet(), 'item_commando_boots_02_01')
			pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s05.iff', actor.getPlanet(), 'item_commando_pants_02_01')
			shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_shirt_s42.iff', actor.getPlanet(), 'item_commando_shirt_02_01')
			vest = core.objectService.createObject('object/tangible/wearables/vest/shared_vest_s04.iff', actor.getPlanet(), 'item_commando_vest_02_01')
			items.add(boots)
			items.add(pants)
			items.add(shirt)
			items.add(vest)

	elif profession =='spy_1a':
		if "itho" in actor.getTemplate():
			belt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_belt_s02.iff', actor.getPlanet())
			pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s02.iff', actor.getPlanet())
			vest = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_vest_s02.iff', actor.getPlanet())
			items.add(belt)
			items.add(pants)
			items.add(shirt)
			items.add(vest)
		if "wookie" in actor.getTemplate():
			gloves = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_gloves_s02.iff', actor.getPlanet())
			skirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_skirt_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_shirt_s02.iff', actor.getPlanet())
			hood = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_hood_s02.iff', actor.getPlanet())
			items.add(gloves)
			items.add(skirt)
			items.add(shirt)
			items.add(hood)
		else:
			boots = core.objectService.createObject('object/tangible/wearables/boots/shared_boots_s12.iff', actor.getPlanet(), 'item_spy_boots_02_01')
			gloves = core.objectService.createObject('object/tangible/wearables/gloves/shared_gloves_s12.iff', actor.getPlanet(), 'item_spy_gloves_02_01')
			pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s30.iff', actor.getPlanet(), 'item_spy_pants_02_01')
			shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_shirt_s30.iff', actor.getPlanet(), 'item_spy_shirt_02_01')
			items.add(boots)
			items.add(gloves)
			items.add(pants)
			items.add(shirt)
			
	elif profession == 'medic_1a':
		if "itho" in actor.getTemplate():
			belt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_belt_s02.iff', actor.getPlanet())
			pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s02.iff', actor.getPlanet())
			vest = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_vest_s02.iff', actor.getPlanet())
			items.add(belt)
			items.add(pants)
			items.add(shirt)
			items.add(vest)
		if "wookie" in actor.getTemplate():
			gloves = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_gloves_s02.iff', actor.getPlanet())
			skirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_skirt_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_shirt_s02.iff', actor.getPlanet())
			hood = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_hood_s02.iff', actor.getPlanet())
			items.add(gloves)
			items.add(skirt)
			items.add(shirt)
			items.add(hood)
		else:
			boots = core.objectService.createObject('object/tangible/wearables/boots/shared_boots_s22.iff', actor.getPlanet(), 'item_medic_boots_02_01')
			pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s08.iff', actor.getPlanet(), 'item_medic_pants_02_01')
			shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_shirt_s27.iff', actor.getPlanet(), 'item_medic_shirt_02_01')
			vest = core.objectService.createObject('object/tangible/wearables/vest/shared_vest_s05.iff', actor.getPlanet(), 'item_medic_vest_02_01')
			items.add(boots)
			items.add(pants)
			items.add(shirt)
			items.add(vest)
		
	elif "trader" in profession:
		if "itho" in actor.getTemplate():
			belt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_belt_s02.iff', actor.getPlanet())
			pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s02.iff', actor.getPlanet())
			vest = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_vest_s02.iff', actor.getPlanet())
			items.add(belt)
			items.add(pants)
			items.add(shirt)
			items.add(vest)
		if "wookie" in actor.getTemplate():
			gloves = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_gloves_s02.iff', actor.getPlanet())
			skirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_skirt_s02.iff', actor.getPlanet())	
			shirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_shirt_s02.iff', actor.getPlanet())
			hood = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_hood_s02.iff', actor.getPlanet())
			items.add(gloves)
			items.add(skirt)
			items.add(shirt)
			items.add(hood)
		else:
			shoes = core.objectService.createObject('object/tangible/wearables/shoes/shared_shoes_s01.iff', actor.getPlanet(), 'item_trader_shoes_02_01')
			pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s15.iff', actor.getPlanet(), 'item_trader_pants_02_01')
			shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_shirt_s05.iff', actor.getPlanet(), 'item_trader_shirt_02_01')
			belt = core.objectService.createObject('object/tangible/wearables/armor/zam/shared_armor_zam_wesell_belt.iff', actor.getPlanet(), 'item_trader_belt_02_01')
			items.add(shoes)
			items.add(pants)
			tems.add(shirt)
			items.add(belt)
	else:
		actor.sendSystemMessage('@base_player:not_correct_skill', 0)
		
	items.add(createProfessionItem(core, actor))
	core.playerService.giveItems(actor, items)
	core.objectService.destroyObject(object)
	return
	

def createProfessionItem(core, actor):
	profession = actor.getSlottedObject('ghost').getProfession()

	if profession == 'bounty_hunter_1a':
		return core.objectService.createObject('object/weapon/ranged/carbine/shared_carbine_dh17_black_npe.iff', actor.getPlanet(), 'weapon_npe_carbine_bh_03_01')

	elif profession == 'commando_1a':
		return core.objectService.createObject('object/weapon/ranged/carbine/shared_carbine_dh17_black_npe.iff', actor.getPlanet(), 'weapon_npe_commando_carbine_03_01')
	
	elif profession == 'entertainer_1a':
		print("Entertainer Professionitem disabled for now")
		#return core.objectService.createObject('object/tangible/dance_prob/shared_prop_sparkler_l.iff', actor.getPlanet())

	elif profession == 'force_sensitive_1a':
		return core.objectService.createObject('object/weapon/melee/polearm/shared_polearm_vibro_axe_npe.iff', actor.getPlanet(), 'weapon_polearm_02_03')

	elif profession == 'medic_1a':
		return core.objectService.createObject('object/weapon/ranged/pistol/shared_pistol_scout_blaster_npe.iff', actor.getPlanet(), 'weapon_npe_medic_pistol_03_01')                  

	elif profession == 'officer_1a':
		return core.objectService.createObject('object/weapon/ranged/pistol/shared_pistol_scout_blaster_npe.iff', actor.getPlanet(), 'item_npe_officer_sidearm')       

	elif profession == 'smuggler_1a':
		return core.objectService.createObject('object/weapon/ranged/pistol/shared_pistol_dl44_metal.iff', actor.getPlanet(), 'item_npe_smuggler_han_solo_gun')
 
	elif profession == 'spy_1a':
		return core.objectService.createObject('object/weapon/ranged/carbine/shared_carbine_dh17_black_npe.iff', actor.getPlanet(), 'weapon_npe_carbine_spy_03_01')                    
   
	elif "trader" in profession:
		print("Trader Professionitem disabled until crafting is in")
		#return core.objectService.createObject('object/tangible/crafting/station/shared_generic_generic_tool.iff', actor.getPlanet(), 'item_npe_gen_craft_tool_trader_03_01')

	return


	
