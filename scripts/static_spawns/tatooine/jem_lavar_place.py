import sys
# Project SWG:   Jem Levar Place Spawn
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	jem = stcSvc.spawnObject('object/mobile/shared_dressed_legacy_lavar.iff', 'tatooine', long(0), float(-487.6), float(43.7), float(-7119.5), float(0), float(0))
	jem.setCustomName('Jem Lavar')	
	jem.setOptionsBitmask(256)
	
	#gunrunners
	
	stcSvc.spawnObject('gunrunner_1', 'tatooine', long(0), float(-483), float(42.7), float(-7107), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('gunrunner_1', 'tatooine', long(0), float(-475), float(42), float(-7106), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('gunrunner_1', 'tatooine', long(0), float(-466), float(41.4), float(-7101), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('gunrunner_1', 'tatooine', long(0), float(-476), float(42.9), float(-7119), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('gunrunner_1', 'tatooine', long(0), float(-477), float(43.7), float(-7129), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('gunrunner_1', 'tatooine', long(0), float(-490), float(45.7), float(-7134), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('gunrunner_1', 'tatooine', long(0), float(-495), float(44.3), float(-7124), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('gunrunner_1', 'tatooine', long(0), float(-505), float(47.1), float(-7119), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('gunrunner_1', 'tatooine', long(0), float(-507), float(44.7), float(-7100), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('gunrunner_1', 'tatooine', long(0), float(-494), float(43.9), float(-7108), float(0), float(0), float(0), float(0), 45)

	return
	
