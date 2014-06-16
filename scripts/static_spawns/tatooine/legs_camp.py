import sys
# Project SWG:   Droid Legs Camp:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.

	
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-7201), float(301.1), float(3792), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-7198), float(301.1), float(3797), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-7206), float(301.1), float(3797), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-7215), float(301.1), float(3798), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-7213), float(301.1), float(3809), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-7207), float(301.1), float(3812), float(0), float(0), float(0), float(0), 45)
	
	return
	
	
