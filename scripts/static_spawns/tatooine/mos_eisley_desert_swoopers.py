import sys
# Project SWG:   Mos Eisley Desert Swoopers:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	desert_swooper = stcSvc.spawnObject('desert_swooper', 'tatooine', long(0), float(3319), float(5), float(-5125), float(0), float(0), float(0), float(0), 30)
	desert_swooper1 = stcSvc.spawnObject('desert_swooper', 'tatooine', long(0), float(3309), float(5), float(-5121), float(0), float(0), float(0), float(0), 30)
	desert_swooper2 = stcSvc.spawnObject('desert_swooper', 'tatooine', long(0), float(3325), float(5), float(-5112), float(0), float(0), float(0), float(0), 30)
	desert_swooper3 = stcSvc.spawnObject('desert_swooper', 'tatooine', long(0), float(3334), float(5), float(-5130), float(0), float(0), float(0), float(0), 30)
	desert_swooper4 = stcSvc.spawnObject('desert_swooper', 'tatooine', long(0), float(3315), float(5), float(-5122), float(0), float(0), float(0), float(0), 30)
	desert_swooper5 = stcSvc.spawnObject('desert_swooper', 'tatooine', long(0), float(3322), float(5), float(-5138), float(0), float(0), float(0), float(0), 30)
	desert_swooper6 = stcSvc.spawnObject('desert_swooper', 'tatooine', long(0), float(3331), float(5), float(-5119), float(0), float(0), float(0), float(0), 30)
	desert_swooper7 = stcSvc.spawnObject('desert_swooper', 'tatooine', long(0), float(3292), float(5), float(-5155), float(0), float(0), float(0), float(0), 30)
	desert_swooper8 = stcSvc.spawnObject('desert_swooper', 'tatooine', long(0), float(3312), float(5), float(-5155), float(0), float(0), float(0), float(0), 30)
	desert_swooper9 = stcSvc.spawnObject('desert_swooper', 'tatooine', long(0), float(3303), float(5), float(-5145), float(0), float(0), float(0), float(0), 30)
	desert_swooper10 = stcSvc.spawnObject('desert_swooper', 'tatooine', long(0), float(3296), float(5), float(-5121), float(0), float(0), float(0), float(0), 30)
	desert_swooper11 = stcSvc.spawnObject('desert_swooper', 'tatooine', long(0), float(3280), float(5), float(-5112), float(0), float(0), float(0), float(0), 30)
	desert_swooper12 = stcSvc.spawnObject('desert_swooper', 'tatooine', long(0), float(3294), float(5), float(-5130), float(0), float(0), float(0), float(0), 30)
	desert_swooper13 = stcSvc.spawnObject('desert_swooper', 'tatooine', long(0), float(3303), float(5), float(-5119), float(0), float(0), float(0), float(0), 30)
	desert_swooper14 = stcSvc.spawnObject('desert_swooper', 'tatooine', long(0), float(3290), float(5), float(-5138), float(0), float(0), float(0), float(0), 30)
	
	desert_swooper_leader = stcSvc.spawnObject('desert_swooper_leader', 'tatooine', long(0), float(3313), float(5), float(-5138), float(0), float(0), float(0), float(0), 30)
	desert_swooper_leader1 = stcSvc.spawnObject('desert_swooper_leader', 'tatooine', long(0), float(3316), float(5), float(-5131), float(0), float(0), float(0), float(0), 30)
	
	return
	
