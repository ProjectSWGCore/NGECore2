import sys
# Project SWG:   Wayfar:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.

	
	#Junkdealer
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(-5240.87), float(75), float(-641.015), float(0.879), float(0.476))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(-5132.33), float(75), float(-6584.92), float(0.637), float(0.771))
	return
	
