import sys
# Project SWG:   Jabba TP Romo Vax Bunker:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
		
	stcSvc.spawnObject('romovax_henchman', 'tatooine', long(0), float(-5079.1), float(47.9), float(-6970.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', long(0), float(-5063.5), float(49.2), float(-6998.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', long(0), float(-5051.8), float(45.7), float(-6989.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', long(0), float(-5030.7), float(46.5), float(-6972.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', long(0), float(-5019), float(48.3), float(-6946.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', long(0), float(-5045.8), float(42.6), float(-6936), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', long(0), float(-5053.7), float(43.6), float(-6961.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', long(0), float(-5057.5), float(43.9), float(-6961.5), float(0), float(0), float(0), float(0), 45)
	
	# TODO Check all NPCs for personalized scripting, change format.
	bunker = core.objectService.getObject(long(-466404037494797872))
	
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(2), float(-3.8), float(0.3), float(2.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(3), float(3.6), float(0.3), float(-3.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(5), float(29.8), float(-12), float(25.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(5), float(32.7), float(-12), float(35), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(5), float(22.9), float(-12), float(30.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(4), float(3.9), float(-12), float(21), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(4), float(3.9), float(-12), float(38.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(6), float(3.5), float(-16), float(53), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(8), float(58.8), float(-16), float(61), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(8), float(74.4), float(-16), float(66.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(8), float(68.3), float(-16), float(79.2), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(8), float(44.6), float(-16), float(82.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(7), float(26), float(-16), float(79), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(7), float(6.9), float(-16), float(78.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(7), float(-5.2), float(-16), float(77.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(7), float(-19.9), float(-16), float(78.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('romovax_henchman', 'tatooine', bunker.getCellByCellNumber(9), float(32.4), float(-14), float(78.7), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('fighting_romo_vax', 'tatooine', bunker.getCellByCellNumber(9), float(-43.5), float(-14), float(-78.9), float(0), float(0), float(0), float(0), 45)
	return
	
