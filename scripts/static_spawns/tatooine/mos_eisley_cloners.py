import sys
# Project SWG: Cloners:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	cloner1 = core.objectService.getObject(long(1106500))
	
	stcSvc.spawnObject('object/mobile/shared_21b_surgical_droid.iff', 'tatooine', cloner1.getCellByCellNumber(2), float(-3.2), float(0.1), float(0.6), float(0), float(0), float(0), float(0))
	
	
	cloner2 = core.objectService.getObject(long(9655494))
	
	stcSvc.spawnObject('object/mobile/shared_21b_surgical_droid.iff', 'tatooine', cloner2.getCellByCellNumber(2), float(-0.4), float(0.2), float(-2.5), float(0), float(0), float(0), float(0))

	
	return
	
	
