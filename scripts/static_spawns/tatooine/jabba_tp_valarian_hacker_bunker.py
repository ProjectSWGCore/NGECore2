import sys
# Project SWG:   Jabba TP Valarian Hacker Bunker:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	
	stcSvc.spawnObject('valarian_hacker', 'tatooine', long(0), float(-5371.3), float(64.1), float(-6897.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_hacker', 'tatooine', long(0), float(-5372.5), float(64.3), float(-6902.4), float(0), float(0), float(0), float(0), 45)
	
	bunker = core.objectService.getObject(long(-466404036467088174))
		
	stcSvc.spawnObject('valarian_hacker', 'tatooine', bunker.getCellByCellNumber(2), float(-3.7), float(0.3), float(3.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_hacker', 'tatooine', bunker.getCellByCellNumber(3), float(3.8), float(-0.3), float(-4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_hacker', 'tatooine', bunker.getCellByCellNumber(4), float(3.4), float(-3.3), float(4.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_hacker', 'tatooine', bunker.getCellByCellNumber(5), float(-2), float(-6.8), float(-8.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_hacker', 'tatooine', bunker.getCellByCellNumber(5), float(-7.4), float(-6.8), float(-1.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_hacker', 'tatooine', bunker.getCellByCellNumber(6), float(-7), float(-6.8), float(-14.5), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_hacker', 'tatooine', bunker.getCellByCellNumber(6), float(6.7), float(-6.8), float(-14.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_hacker', 'tatooine', bunker.getCellByCellNumber(7), float(5), float(-6.8), float(-1.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_hacker', 'tatooine', bunker.getCellByCellNumber(8), float(-2), float(-13.8), float(8.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_hacker', 'tatooine', bunker.getCellByCellNumber(9), float(1.5), float(-13.8), float(-3.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_hacker', 'tatooine', bunker.getCellByCellNumber(9), float(-6.7), float(-13.8), float(-15.1), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_hacker', 'tatooine', bunker.getCellByCellNumber(9), float(2.7), float(-13.8), float(-11.9), float(0), float(0), float(0), float(0), 45)

	return
	
