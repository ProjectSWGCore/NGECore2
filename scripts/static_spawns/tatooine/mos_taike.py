import sys
# Project SWG:   Mos Taike:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.

	kara = stcSvc.spawnObject('kara', 'tatooine', long(0), float(3839), float(16.13), float(2335), float(-0.1), float(0.98))
	
	#Junkdealer
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(3902), float(33), float(2362), float(0), float(0))
	return
	
