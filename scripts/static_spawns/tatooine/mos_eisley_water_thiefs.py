# Project SWG:   water thief:  Static Spawns
# (C)2014 ProjectSWG

import sys
from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	aiSvc = core.aiService

	# Outside of Tusken camp
	water_thief = stcSvc.spawnObject('water_thief', 'tatooine', long(0), float(3079), float(7), float(-4370), float(0), float(0), float(0), float(0), 45)
	water_thief1 = stcSvc.spawnObject('water_thief', 'tatooine', long(0), float(3075), float(8), float(-4350), float(0), float(0), float(0), float(0), 45)	
	water_thief2 = stcSvc.spawnObject('water_thief', 'tatooine', long(0), float(3097), float(8), float(-4350), float(0), float(0), float(0), float(0), 45)	
	water_thief3 = stcSvc.spawnObject('water_thief', 'tatooine', long(0), float(3094), float(6), float(-4373), float(0), float(0), float(0), float(0), 45)	
	water_thief4 = stcSvc.spawnObject('water_thief', 'tatooine', long(0), float(3065), float(5), float(-4377), float(0), float(0), float(0), float(0), 45)	
	water_thief5 = stcSvc.spawnObject('water_thief', 'tatooine', long(0), float(3057), float(6), float(-4359), float(0), float(0), float(0), float(0), 45)
	water_thief6 = stcSvc.spawnObject('water_thief', 'tatooine', long(0), float(3106), float(9), float(-4350), float(0), float(0), float(0), float(0), 45)		
	water_thief7 = stcSvc.spawnObject('water_thief', 'tatooine', long(0), float(3086), float(9), float(-4342), float(0), float(0), float(0), float(0), 45)
	water_thief8 = stcSvc.spawnObject('water_thief', 'tatooine', long(0), float(3065), float(8), float(-4348), float(0), float(0), float(0), float(0), 45)
	water_thief9 = stcSvc.spawnObject('water_thief', 'tatooine', long(0), float(3054), float(5), float(-4378), float(0), float(0), float(0), float(0), 45)
	
	aiSvc.setLoiter(water_thief1, float(1), float(8)) 
	aiSvc.setLoiter(water_thief2, float(1), float(8)) 
	aiSvc.setLoiter(water_thief3, float(1), float(8)) 
	aiSvc.setLoiter(water_thief4, float(1), float(8)) 
	aiSvc.setLoiter(water_thief5, float(1), float(8))  
	aiSvc.setLoiter(water_thief6, float(1), float(8))
	aiSvc.setLoiter(water_thief7, float(1), float(8)) 
	aiSvc.setLoiter(water_thief8, float(1), float(8)) 
	aiSvc.setLoiter(water_thief9, float(1), float(8)) 
	
	return	