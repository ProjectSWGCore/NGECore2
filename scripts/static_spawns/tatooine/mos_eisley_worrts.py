import sys
# Project SWG:   MosEisley Worrts:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# Tame Worrts
	tame_worrt1 = stcSvc.spawnObject('tame_worrt', 'tatooine', long(0), float(3153), float(4), float(-4945), float(0), float(0), float(0), float(0), 30)	
	tame_worrt11 = stcSvc.spawnObject('tame_worrt', 'tatooine', long(0), float(3143), float(4), float(-4935), float(0), float(0), float(0), float(0), 30)	
	tame_worrt12 = stcSvc.spawnObject('tame_worrt', 'tatooine', long(0), float(3123), float(4), float(-4925), float(0), float(0), float(0), float(0), 30)	
	tame_worrt13 = stcSvc.spawnObject('tame_worrt', 'tatooine', long(0), float(3163), float(4), float(-4955), float(0), float(0), float(0), float(0), 30)	
	tame_worrt14 = stcSvc.spawnObject('tame_worrt', 'tatooine', long(0), float(3173), float(4), float(-4965), float(0), float(0), float(0), float(0), 30)	
	tame_worrt15 = stcSvc.spawnObject('tame_worrt', 'tatooine', long(0), float(3145), float(4), float(-4950), float(0), float(0), float(0), float(0), 30)	
	
	# Tame Worrts
	tame_worrt2 = stcSvc.spawnObject('tame_worrt', 'tatooine', long(0), float(3125), float(4), float(-4726), float(0), float(0), float(0), float(0), 30)	
	tame_worrt21 = stcSvc.spawnObject('tame_worrt', 'tatooine', long(0), float(3115), float(4), float(-4716), float(0), float(0), float(0), float(0), 30)	
	tame_worrt22 = stcSvc.spawnObject('tame_worrt', 'tatooine', long(0), float(3105), float(4), float(-4706), float(0), float(0), float(0), float(0), 30)	
	tame_worrt23 = stcSvc.spawnObject('tame_worrt', 'tatooine', long(0), float(3135), float(4), float(-4736), float(0), float(0), float(0), float(0), 30)	
	tame_worrt24 = stcSvc.spawnObject('tame_worrt', 'tatooine', long(0), float(3145), float(4), float(-4746), float(0), float(0), float(0), float(0), 30)	
	tame_worrt25 = stcSvc.spawnObject('tame_worrt', 'tatooine', long(0), float(3120), float(4), float(-4721), float(0), float(0), float(0), float(0), 30)	

	
	return