import sys
# Project SWG:   Jabba TP Alkharan Camp:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	stcSvc.spawnObject('wild_zucca_boar', 'tatooine', long(0), float(-4502), float(45.3), float(-4023), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wild_zucca_boar', 'tatooine', long(0), float(-4489), float(45.6), float(-4020.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wild_zucca_boar', 'tatooine', long(0), float(-4476.6), float(44.6), float(-4038.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wild_zucca_boar', 'tatooine', long(0), float(-4479.8), float(43.1), float(-4048.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wild_zucca_boar', 'tatooine', long(0), float(-4443.6), float(43.6), float(-4024.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wild_zucca_boar', 'tatooine', long(0), float(-4445.1), float(42.1), float(-4044.9), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('wild_zucca_boar', 'tatooine', long(0), float(-4465.9), float(41.6), float(-4064.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wild_zucca_boar', 'tatooine', long(0), float(-4480.5), float(41.3), float(-4069.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('wild_zucca_boar', 'tatooine', long(0), float(-4501.2), float(43.6), float(-4040.9), float(0), float(0), float(0), float(0), 45)
	return
	
