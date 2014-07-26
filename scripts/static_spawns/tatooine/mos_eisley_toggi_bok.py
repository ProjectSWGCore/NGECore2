import sys
# Project SWG:   Mos Eisley Toogi Bok's place:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	#toggi bock
	
	stcSvc.spawnObject('togibok', 'tatooine', long(0), float(2756), float(7), float(-4567), float(0), float(0), float(0), float(0))
	# Thiefs
	
	thiefs = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2780), float(6), float(-4579), float(0), float(0), float(0), float(0), 30)
	thiefs1 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2781), float(6), float(-4604), float(0), float(0), float(0), float(0), 30)	
	thiefs2 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2732), float(9), float(-4619), float(0), float(0), float(0), float(0), 30)	
	thiefs3 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2717), float(11), float(-4594), float(0), float(0), float(0), float(0), 30)	
	thiefs4 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2733), float(7), float(-4563), float(0), float(0), float(0), float(0), 30)	
	thiefs5 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2762), float(7), float(-4535), float(0), float(0), float(0), float(0), 30)	
	thiefs6 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2793), float(7), float(-4554), float(0), float(0), float(0), float(0), 30)	
	thiefs7 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2771), float(6), float(-4594), float(0), float(0), float(0), float(0), 30)
	thiefs8 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2763), float(7), float(-4623), float(0), float(0), float(0), float(0), 30)
	thiefs9 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2809), float(8), float(-4562), float(0), float(0), float(0), float(0), 30)	
	thiefs10 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2807), float(7), float(-4592), float(0), float(0), float(0), float(0), 30)	
	thiefs11 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2793), float(6), float(-4622), float(0), float(0), float(0), float(0), 30)	
	thiefs12 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2748), float(7), float(-4593), float(0), float(0), float(0), float(0), 30)	
	thiefs13 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2708), float(11), float(-4566), float(0), float(0), float(0), float(0), 30)	
	thiefs14 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2742), float(9), float(-4541), float(0), float(0), float(0), float(0), 30)	
	thiefs15 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2786), float(7), float(-4535), float(0), float(0), float(0), float(0), 30)
	thiefs16 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2803), float(6), float(-4578), float(0), float(0), float(0), float(0), 30)	
	thiefs17 = stcSvc.spawnObject('thief', 'tatooine', long(0), float(2800), float(6), float(-4619), float(0), float(0), float(0), float(0), 30)					
	
	return
	
