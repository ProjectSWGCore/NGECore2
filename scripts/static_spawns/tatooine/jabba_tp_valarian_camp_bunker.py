import sys
# Project SWG:   Jabba TP Valarian Camp:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	bunker = core.objectService.getObject(long(-466404038502638198))
	
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(3), float(-3.7), float(0.3), float(-4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(4), float(-3.2), float(-3.3), float(5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(5), float(3.4), float(-6.8), float(-2.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(5), float(6.7), float(-6.8), float(-4.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(5), float(3.7), float(-6.8), float(-8.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(6), float(4.4), float(-10.3), float(-18.7), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(6), float(-4), float(-13.8), float(-18.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(7), float(-3.3), float(-13.7), float(-10.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(7), float(-3.4), float(-13.7), float(-2.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(7), float(-3.5), float(-13.7), float(-4.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(8), float(-8.6), float(-13.8), float(12.2), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(8), float(7.7), float(-13.8), float(12.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(9), float(15.4), float(-13.8), float(14.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(9), float(16), float(-13.8), float(6.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(10), float(15.8), float(-13.8), float(-9.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_bookie', 'tatooine', bunker.getCellByCellNumber(10), float(11.9), float(-11.5), float(-21), float(0), float(0), float(0), float(0), 45)
	return
	
