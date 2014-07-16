import sys
# Project SWG:   Jabba TP Alkharan Camp:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-4505.5), float(35), float(-2270.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-4511), float(35), float(-2261.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-4522.7), float(35), float(-2262.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-4519.7), float(35), float(-2253.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tusken_follower', 'tatooine', long(0), float(-4529.8), float(35), float(-2253.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dwarf_bantha', 'tatooine', long(0), float(-4524.3), float(35), float(-2257.7), float(0), float(0), float(0), float(0), 45)	

	return
	
