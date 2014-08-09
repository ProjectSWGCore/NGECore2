import sys
# Project SWG:   Mos Espa:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	#Wattos shop
	building = core.objectService.getObject(long(-466404040703268293)) 
	
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	# Cantina Interior
	dalaSocuna = stcSvc.spawnObject('object/mobile/shared_space_rebel_tier1_tatooine_socuna.iff', 'tatooine', long(1256068), float(-28.4), float(-0.5), float(9.4), float(0.33), float(0.94))
	#dalaSocuna.setCustomName2('Commander Da\'la Socuna')
	#dalaSocuna.setOptionsBitmask(256)
	
	#Watto's Shop  ****TODO:  Get Proper cells for Watto's Shop once Buildout structures are spawning as intended again.
	
	watto = stcSvc.spawnObject('object/mobile/shared_watto.iff', 'tatooine', building.getCellByCellNumber(2), float(4.7), float(-0.5), float(2.4), float(-0.71), float(0), float(0.70), float(0))
	#watto.setCustomName2('Watto')
	#watto.setOptionsBitmask(256)
	
	#Exterior Uninteractable NPCs
	commoner18 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_zabrak_female_02.iff', 'tatooine', long(0), float(-2915.3), float(5.0), float(2148.5), float(0), float(0)) 
	commoner18.setCustomName2('a Commoner')
	commoner18.setOptionsBitmask(256)
	
	jawa10 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(-2966.3), float(5.0), float(2196.9), float(0), float(0)) 
	jawa10.setCustomName2('a Jawa')
	jawa10.setOptionsBitmask(256)

	commoner19 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_human_male_06.iff', 'tatooine', long(0), float(-2960.4), float(5.0), float(2271.3), float(0), float(0)) 
	commoner19.setCustomName2('a Commoner')
	commoner19.setOptionsBitmask(256)
	
	commoner20 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_devaronian_male_01.iff', 'tatooine', long(0), float(-2934.3), float(5.0), float(2298.9), float(0.71), float(0.71)) 
	commoner20.setCustomName2('a Commoner')
	commoner20.setOptionsBitmask(256)
	
	commoner21 = stcSvc.spawnObject('object/mobile/shared_dressed_noble_human_female_01.iff', 'tatooine', long(0), float(-2897.7), float(5.0), float(2345.4), float(0.71), float(0.71)) 
	commoner21.setCustomName2('a Commoner')
	commoner21.setOptionsBitmask(256)
	
	commoner22 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_twilek_male_01.iff', 'tatooine', long(0), float(-2763.8), float(5.0), float(2305.2), float(0.71), float(0.71)) 
	commoner22.setCustomName2('a Commoner')
	commoner22.setOptionsBitmask(256)
	
	jawa11 = stcSvc.spawnObject('object/mobile/shared_jawa.iff', 'tatooine', long(0), float(-2936.8), float(5.0), float(2078.4), float(0.95105), float(0.3090)) 
	jawa11.setCustomName2('a Jawa')
	jawa11.setOptionsBitmask(256)
	
	businessman4 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_tatooine_trandoshan_male_02.iff', 'tatooine', long(0), float(-2914.2), float(5.0), float(2022.4), float(0), float(0)) 
	businessman4.setCustomName2('a Businessman')
	businessman4.setOptionsBitmask(256)
	
	commoner23 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_naboo_moncal_male_01.iff', 'tatooine', long(0), float(-2904.1), float(5.0), float(1965.3), float(0.71), float(0.71)) 
	commoner23.setCustomName2('a Commoner')
	commoner23.setOptionsBitmask(256)
	
	#Junkdealer will be added as soon as i can find coords
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(-3060.50), float(5), float(2181.71), float(0), float(1))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(-2924.53), float(5), float(2173.97), float(-0.391), float(0.920))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(-2977), float(5.5), float(2327), float(-0.490), float(0.872))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(-3008), float(5.5), float(2347), float(0.885), float(0.464))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(-2903), float(5), float(2446), float(0.782), float(-0.622))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(-2933), float(5), float(2124), float(0.446), float(0.896))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(-2846), float(5), float(2196), float(-0.389), float(0.9211))
	return
	
