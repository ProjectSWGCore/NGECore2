import sys
# Project SWG:   Moseisley Tusken warriors:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# Tusken Warriors
	warrior = stcSvc.spawnObject('tusken_warrior', 'tatooine', long(0), float(4288), float(8), float(-4729), float(0), float(0), float(0), float(0), 45)	
	warrior1 = stcSvc.spawnObject('tusken_warrior', 'tatooine', long(0), float(4290), float(8), float(-4725), float(0), float(0), float(0), float(0), 45)	
	warrior2 = stcSvc.spawnObject('tusken_warrior', 'tatooine', long(0), float(4292), float(8), float(-4721), float(0), float(0), float(0), float(0), 45)	
	warrior3 = stcSvc.spawnObject('tusken_warrior', 'tatooine', long(0), float(4290), float(8), float(-4717), float(0), float(0), float(0), float(0), 45)	
	warrior4 = stcSvc.spawnObject('tusken_warrior', 'tatooine', long(0), float(4288), float(8), float(-4713), float(0), float(0), float(0), float(0), 45)	
	warrior5 = stcSvc.spawnObject('tusken_warrior', 'tatooine', long(0), float(4290), float(8), float(-4733), float(0), float(0), float(0), float(0), 45)	
	warrior6 = stcSvc.spawnObject('tusken_warrior', 'tatooine', long(0), float(4292), float(8), float(-4737), float(0), float(0), float(0), float(0), 45)	
	warrior8 = stcSvc.spawnObject('tusken_warrior', 'tatooine', long(0), float(4290), float(8), float(-4741), float(0), float(0), float(0), float(0), 45)	
	warrior9 = stcSvc.spawnObject('tusken_warrior', 'tatooine', long(0), float(4288), float(8), float(-4745), float(0), float(0), float(0), float(0), 45)	
	warrior0 = stcSvc.spawnObject('tusken_warrior', 'tatooine', long(0), float(4286), float(8), float(-4749), float(0), float(0), float(0), float(0), 45)	
	