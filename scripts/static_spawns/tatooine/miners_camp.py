import sys
# Project SWG:   Miners Camp:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.

	
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-5581), float(322.7), float(1738), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-5582), float(322.7), float(1746), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-5569), float(322.7), float(1744), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-5568), float(322.7), float(1734), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-5586), float(322.7), float(1733), float(0), float(0), float(0), float(0), 45)
	
	return
	
	
