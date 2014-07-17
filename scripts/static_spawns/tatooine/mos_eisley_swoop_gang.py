import sys
# Project SWG:   Mos Eisley Swoop gang:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	
	#Enforcers
	
	swoop_gang_enforcer = stcSvc.spawnObject('swoop_gang_enforcer', 'tatooine', long(0), float(3183), float(5), float(-4786), float(0), float(0), float(0), float(0), 30)	
	swoop_gang_enforcer1 = stcSvc.spawnObject('swoop_gang_enforcer', 'tatooine', long(0), float(3208), float(5), float(-4824), float(0), float(0), float(0), float(0), 30)	
		
	#Punk
	
	swoop_gang_punk = stcSvc.spawnObject('swoop_gang_punk', 'tatooine', long(0), float(3194), float(5), float(-4839), float(0), float(0), float(0), float(0), 30)	
	swoop_gang_punk1 = stcSvc.spawnObject('swoop_gang_punk', 'tatooine', long(0), float(3167), float(5), float(-4795), float(0), float(0), float(0), float(0), 30)
	swoop_gang_punk2 = stcSvc.spawnObject('swoop_gang_punk', 'tatooine', long(0), float(3207), float(5), float(-4808), float(0), float(0), float(0), float(0), 30)
	swoop_gang_punk3 = stcSvc.spawnObject('swoop_gang_punk', 'tatooine', long(0), float(3149), float(5), float(-4816), float(0), float(0), float(0), float(0), 30)
		
	#Rogue
	
	swoop_gang_rogue = stcSvc.spawnObject('swoop_gang_rogue', 'tatooine', long(0), float(3151), float(5), float(-4802), float(0), float(0), float(0), float(0), 30)	
	swoop_gang_rogue1 = stcSvc.spawnObject('swoop_gang_rogue', 'tatooine', long(0), float(3199), float(5), float(-4799), float(0), float(0), float(0), float(0), 30)	
	swoop_gang_rogue2 = stcSvc.spawnObject('swoop_gang_rogue', 'tatooine', long(0), float(3180), float(5), float(-4811), float(0), float(0), float(0), float(0), 30)	
	#thug
	
	swoop_gang_thug = stcSvc.spawnObject('swoop_gang_thug', 'tatooine', long(0), float(3220), float(5), float(-4800), float(0), float(0), float(0), float(0), 30)	
	swoop_gang_thug1 = stcSvc.spawnObject('swoop_gang_thug', 'tatooine', long(0), float(3169), float(5), float(-4823), float(0), float(0), float(0), float(0), 30)
	swoop_gang_thug2 = stcSvc.spawnObject('swoop_gang_thug', 'tatooine', long(0), float(3168), float(5), float(-4773), float(0), float(0), float(0), float(0), 30)
	
	return
	
