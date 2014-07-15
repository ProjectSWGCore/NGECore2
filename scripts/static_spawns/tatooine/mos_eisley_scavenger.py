# Project SWG:   Mos Eisley scavenger:  Static Spawns
# (C)2014 ProjectSWG

import sys
from resources.datatables import Options
from resources.datatables import State
from java.util import Vector
from engine.resources.scene import Point3D

from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	aiSvc = core.aiService
	
	#Enforcers
	
	scavenger = stcSvc.spawnObject('scavenger', 'tatooine', long(0), float(3102), float(9), float(-4951), float(0), float(0), float(0), float(0), 30)	
	scavenger1 = stcSvc.spawnObject('scavenger', 'tatooine', long(0), float(3110), float(8), float(-4952), float(0), float(0), float(0), float(0), 30)	
	scavenger2 = stcSvc.spawnObject('scavenger', 'tatooine', long(0), float(3109), float(7), float(-4961), float(0), float(0), float(0), float(0), 30)	
	scavenger3 = stcSvc.spawnObject('scavenger', 'tatooine', long(0), float(3096), float(9), float(-4938), float(0), float(0), float(0), float(0), 30)	
	scavenger4 = stcSvc.spawnObject('scavenger', 'tatooine', long(0), float(3080), float(11), float(-4942), float(0), float(0), float(0), float(0), 30)	
	scavenger5 = stcSvc.spawnObject('scavenger', 'tatooine', long(0), float(3089), float(10), float(-4961), float(0), float(0), float(0), float(0), 30)	
	scavenger6 = stcSvc.spawnObject('scavenger', 'tatooine', long(0), float(3106), float(7), float(-4969), float(0), float(0), float(0), float(0), 30)	
	scavenger7 = stcSvc.spawnObject('scavenger', 'tatooine', long(0), float(3100), float(8), float(-4973), float(0), float(0), float(0), float(0), 30)	
	scavenger8 = stcSvc.spawnObject('scavenger', 'tatooine', long(0), float(3067), float(9), float(-4932), float(0), float(0), float(0), float(0), 30)	
	scavenger9 = stcSvc.spawnObject('scavenger', 'tatooine', long(0), float(3061), float(6), float(-4951), float(0), float(0), float(0), float(0), 30)	
	scavenger10 = stcSvc.spawnObject('scavenger', 'tatooine', long(0), float(3077), float(3), float(-4961), float(0), float(0), float(0), float(0), 30)	
	scavenger11 = stcSvc.spawnObject('scavenger', 'tatooine', long(0), float(3071), float(3), float(-4971), float(0), float(0), float(0), float(0), 30)	
	scavenger12 = stcSvc.spawnObject('scavenger', 'tatooine', long(0), float(3047), float(7), float(-4957), float(0), float(0), float(0), float(0), 30)	
												
	aiSvc.setLoiter(scavenger, float(1), float(8))  
	aiSvc.setLoiter(scavenger1, float(1), float(8))
	aiSvc.setLoiter(scavenger2, float(1), float(8)) 
	aiSvc.setLoiter(scavenger3, float(1), float(8)) 
	aiSvc.setLoiter(scavenger4, float(1), float(8)) 
	aiSvc.setLoiter(scavenger5, float(1), float(8)) 
	aiSvc.setLoiter(scavenger6, float(1), float(8)) 
	aiSvc.setLoiter(scavenger7, float(1), float(8)) 
	aiSvc.setLoiter(scavenger8, float(1), float(8)) 
	aiSvc.setLoiter(scavenger9, float(1), float(8)) 
	aiSvc.setLoiter(scavenger10, float(1), float(8)) 
	aiSvc.setLoiter(scavenger11, float(1), float(8))
	aiSvc.setLoiter(scavenger12, float(1), float(8))
	 
	return
	
