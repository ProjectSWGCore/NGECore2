import sys
# Project SWG:   Krayt Skeleton:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	# -6205, 5054
	stcSvc.spawnObject('canyon_krayt_dragon', 'tatooine', long(0), float(-6216.0), float(30.8), float(5033.9), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('juvenile_canyon_krayt_dragon', 'tatooine', long(0), float(-6213.8), float(31.1), float(5021.3), float(0), float(0), float(0), float(0), 1500)	
	
	# giant canyon krayt
	stcSvc.spawnObject('giant_canyon_krayt_dragon', 'tatooine', long(0), float(5728.5), float(56.6), float(3784.7), float(0), float(0), float(0), float(0), 1800)	

	# -6750, 3569
	stcSvc.spawnObject('grand_krayt_dragon', 'tatooine', long(0), float(-6718.3), float(175.6), float(3499.6), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('grand_krayt_dragon', 'tatooine', long(0), float(-6796.1), float(194.0), float(3514.6), float(0), float(0), float(0), float(0), 1800)	
	stcSvc.spawnObject('adolescent_krayt_dragon', 'tatooine', long(0), float(-6789.0), float(127.7), float(3661.2), float(0), float(0), float(0), float(0), 1500)	
	
	return
	
