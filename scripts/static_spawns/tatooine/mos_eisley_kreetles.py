import sys
# Project SWG:   MosEisley Kreetles:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# Carrion Kreetles
	carrion_kreetle1 = stcSvc.spawnObject('carrion_kreetle', 'tatooine', long(0), float(3551), float(4), float(-5064), float(0), float(0), float(0), float(0), 30)
	carrion_kreetle11 = stcSvc.spawnObject('carrion_kreetle', 'tatooine', long(0), float(3545), float(4), float(-5055), float(0), float(0), float(0), float(0), 30)	
	carrion_kreetle12 = stcSvc.spawnObject('carrion_kreetle', 'tatooine', long(0), float(3540), float(4), float(-5050), float(0), float(0), float(0), float(0), 30)	
	carrion_kreetle13 = stcSvc.spawnObject('carrion_kreetle', 'tatooine', long(0), float(3533), float(4), float(-5070), float(0), float(0), float(0), float(0), 30)	
	carrion_kreetle14 = stcSvc.spawnObject('carrion_kreetle', 'tatooine', long(0), float(3555), float(4), float(-5072), float(0), float(0), float(0), float(0), 30)	

	
	carrion_kreetle2 = stcSvc.spawnObject('carrion_kreetle', 'tatooine', long(0), float(3233), float(8), float(-4457), float(0), float(0), float(0), float(0), 30)	
	carrion_kreetle21 = stcSvc.spawnObject('carrion_kreetle', 'tatooine', long(0), float(3223), float(8), float(-4447), float(0), float(0), float(0), float(0), 30)	
	carrion_kreetle22 = stcSvc.spawnObject('carrion_kreetle', 'tatooine', long(0), float(3213), float(8), float(-4455), float(0), float(0), float(0), float(0), 30)	
	carrion_kreetle23 = stcSvc.spawnObject('carrion_kreetle', 'tatooine', long(0), float(3235), float(8), float(-4440), float(0), float(0), float(0), float(0), 30)	
	carrion_kreetle24 = stcSvc.spawnObject('carrion_kreetle', 'tatooine', long(0), float(3245), float(8), float(-4460), float(0), float(0), float(0), float(0), 30)	

		
	carrion_kreetle3 = stcSvc.spawnObject('carrion_kreetle', 'tatooine', long(0), float(3147), float(4), float(-4846), float(0), float(0), float(0), float(0), 30)	
	carrion_kreetle31 = stcSvc.spawnObject('carrion_kreetle', 'tatooine', long(0), float(3137), float(4), float(-4856), float(0), float(0), float(0), float(0), 30)	
	carrion_kreetle32 = stcSvc.spawnObject('carrion_kreetle', 'tatooine', long(0), float(3127), float(4), float(-4836), float(0), float(0), float(0), float(0), 30)
	carrion_kreetle35 = stcSvc.spawnObject('carrion_kreetle', 'tatooine', long(0), float(3145), float(4), float(-4852), float(0), float(0), float(0), float(0), 30)
	carrion_kreetle36 = stcSvc.spawnObject('carrion_kreetle', 'tatooine', long(0), float(3135), float(4), float(-4838), float(0), float(0), float(0), float(0), 30)

			
	return