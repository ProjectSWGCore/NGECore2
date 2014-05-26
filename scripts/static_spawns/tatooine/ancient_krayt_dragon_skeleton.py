import sys
# Project SWG:   Ancient Krayt Skeleton:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	stcSvc.spawnObject('canyon_krayt_dragon', 'tatooine', long(0), float(-4528.8), float(28.3), float(-4302.4), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('juvenile_canyon_krayt_dragon', 'tatooine', long(0), float(-4521.2), float(27.1), float(-4298.1), float(0), float(0), float(0), float(0), 1500)	
	stcSvc.spawnObject('giant_canyon_krayt_dragon', 'tatooine', long(0), float(-4669.8), float(30.0), float(-4477.7), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('grand_krayt_dragon', 'tatooine', long(0), float(-4555.0), float(49.2), float(-4459.3), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('krayt_dragon_ancient', 'tatooine', long(0), float(-4713.3), float(46.5), float(-4288.3), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('adolescent_krayt_dragon', 'tatooine', long(0), float(-4747.2), float(32.5), float(-4424.6), float(0), float(0), float(0), float(0), 1500)	
	
	
	return
	
