import sys
# Project SWG:   Jabba TP spicedealer Camp:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	stcSvc.spawnObject('jabbas_palace_tyrok', 'tatooine', long(0), float(-4933.3), float(31.2), float(-5541.8), float(0), float(0), float(0), float(0), 45)

	stcSvc.spawnObject('mountain_squill', 'tatooine', long(0), float(-4939.9), float(30.6), float(-5547.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('mountain_squill', 'tatooine', long(0), float(-4946.3), float(30.1), float(-5555.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('mountain_squill', 'tatooine', long(0), float(-4938.6), float(29.7), float(-5554.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('mountain_squill', 'tatooine', long(0), float(-4933.1), float(29.5), float(-5554.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('mountain_squill', 'tatooine', long(0), float(-4929.2), float(29.2), float(-5561.3), float(0), float(0), float(0), float(0), 45)

	return
	
