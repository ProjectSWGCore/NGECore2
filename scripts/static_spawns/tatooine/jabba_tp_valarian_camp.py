import sys
# Project SWG:   Jabba TP Valarian Camp:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-6229), float(37.8), float(-5947), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-6238.7), float(37.3), float(-5915.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-6251.7), float(36.4), float(-5956.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-6266.4), float(35.5), float(-5955.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-6273.8), float(34.9), float(-5945), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-6272.7), float(35.4), float(-5930.8), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-6276.1), float(38.3), float(-5919.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-6268.5), float(40.6), float(-5908.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-6255.9), float(40.6), float(-5907.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-6242.6), float(39.9), float(-5912.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-6233.6), float(37.3), float(-5925.8), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-6230.9), float(38), float(-5949.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-6235.2), float(40.2), float(-5964.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-6250.1), float(38.1), float(-5969), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-6271.6), float(36.1), float(-5960.2), float(0), float(0), float(0), float(0), 45)
	return
	
