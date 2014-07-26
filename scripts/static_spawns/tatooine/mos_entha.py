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
	
	#Junkdealer
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(1271), float(7.7), float(2919), float(0), float(0))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(1271), float(7.7), float(2960), float(1), float(0))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(1396), float(7), float(3026), float(0.745), float(0.666))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(1471), float(7), float(3325), float(0), float(1))
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(1463), float(7), float(3126), float(0.71), float(-0.71))
	return
	
