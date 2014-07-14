import sys
# Project SWG:   Jabba TP spicedealer Camp:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	stcSvc.spawnObject('jabbas_palace_delrice_capreese', 'tatooine', long(0), float(-4330.8), float(23), float(-5024.4), float(0), float(0), float(0), float(0), 45)

	return
	
