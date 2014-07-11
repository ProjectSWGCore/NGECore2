import sys
# Project SWG:   Jabba TP Dragonet:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	 #TODO Check all NPCs for personalized scripting, change format.
	
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4643), float(35.2), float(-4727), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4648.5), float(36.1), float(-4743.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4661.9), float(35.6), float(-4751.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4676), float(32.8), float(-4752.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4693.6), float(27.8), float(-4735.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4702.1), float(30.2), float(-4722.8), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4679.9), float(33.3), float(-4711), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4660.6), float(32.5), float(-4701.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4635.9), float(35.7), float(-4701.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4612.9), float(42.9), float(-4712.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4580.9), float(51.3), float(-4737.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4580.3), float(51.6), float(-4753), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4604.5), float(50.7), float(-4765.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4617.6), float(49.3), float(-4783.8), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4661.4), float(32), float(-4723.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4676.4), float(29.8), float(-4736.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4693.2), float(32.5), float(-4759.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4696.7), float(36.8), float(-4769.5), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4683.1), float(39.9), float(-4769.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4668.8), float(41.4), float(-4773.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4653.3), float(43.8), float(-4771.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4634.8), float(46.5), float(-4764.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4615.7), float(46.4), float(-4749.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4621.1), float(41.3), float(-4736.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4597.7), float(41.6), float(-4706), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4613.2), float(33.2), float(-4689.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4636.7), float(28.7), float(-4680.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4677.5), float(28.4), float(-4691.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('dragonet_runt', 'tatooine', long(0), float(-4698.9), float(27.7), float(-4698.8), float(0), float(0), float(0), float(0), 45)

	return
	
