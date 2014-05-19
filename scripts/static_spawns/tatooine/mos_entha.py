import sys
# Project SWG:   Mos Entha:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	#Outside
	ankwee = stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_entha_ankwee.iff', 'tatooine', long(0), float(1351), float(5.0), float(3108), float(0.42), float(0.91))
	ankwee.setCustomName2('An\'kwee')
	ankwee.setOptionsBitmask(256)
	
	
	return
	
