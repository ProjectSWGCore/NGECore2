# Project SWG:   Mos Eisley Toogi Bok's place:  Static Spawns
# (C)2014 ProjectSWG

import sys
from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	aiSvc = core.aiService	
	
	#toggi bok	
	stcSvc.spawnObject('object/mobile/shared_dressed_tatooine_opening_toggi.iff', 'tatooine', long(0), float(2756), float(7), float(-4567), float(0), float(0), float(0), float(0))
	
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
	
	aiSvc.setLoiter(thiefs, float(1), float(8))
	aiSvc.setLoiter(thiefs1, float(1), float(8)) 
	aiSvc.setLoiter(thiefs2, float(1), float(8)) 
	aiSvc.setLoiter(thiefs3, float(1), float(8)) 
	aiSvc.setLoiter(thiefs4, float(1), float(8)) 
	aiSvc.setLoiter(thiefs5, float(1), float(8))  
	aiSvc.setLoiter(thiefs6, float(1), float(8))
	aiSvc.setLoiter(thiefs7, float(1), float(8)) 
	aiSvc.setLoiter(thiefs8, float(1), float(8)) 
	aiSvc.setLoiter(thiefs9, float(1), float(8)) 
	aiSvc.setLoiter(thiefs10, float(1), float(8)) 
	aiSvc.setLoiter(thiefs11, float(1), float(8))
	aiSvc.setLoiter(thiefs12, float(1), float(8)) 
	aiSvc.setLoiter(thiefs13, float(1), float(8))
	aiSvc.setLoiter(thiefs14, float(1), float(8)) 
	aiSvc.setLoiter(thiefs15, float(1), float(8))
	aiSvc.setLoiter(thiefs16, float(1), float(8)) 
	aiSvc.setLoiter(thiefs17, float(1), float(8))
	
	return