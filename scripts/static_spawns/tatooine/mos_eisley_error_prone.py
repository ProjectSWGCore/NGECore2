# Project SWG:   Mos Eisley Error Prone Droids:  Static Spawns
# (C)2014 ProjectSWG

import sys
from resources.datatables import Options
from resources.datatables import State
from java.util import Vector
from engine.resources.scene import Point3D

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	aiSvc = core.aiService
	
	error_prone = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2868), float(39), float(-4814), float(0), float(0), float(0), float(0), 30)
	error_prone1 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2815), float(38), float(-4815), float(0), float(0), float(0), float(0), 30)
	error_prone2 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2875), float(34), float(-4777), float(0), float(0), float(0), float(0), 30)
	error_prone3 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2850), float(37), float(-4786), float(0), float(0), float(0), float(0), 30)
	error_prone4 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2835), float(41), float(-4806), float(0), float(0), float(0), float(0), 30)
	error_prone5 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2839), float(42), float(-4834), float(0), float(0), float(0), float(0), 30)
	error_prone6 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2849), float(40), float(-4848), float(0), float(0), float(0), float(0), 30)
	error_prone7 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2880), float(35), float(-4848), float(0), float(0), float(0), float(0), 30)
	error_prone8 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2878), float(38), float(-4835), float(0), float(0), float(0), float(0), 30)
	error_prone9 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2880), float(38), float(-4819), float(0), float(0), float(0), float(0), 30)
	error_prone10 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2878), float(38), float(-4835), float(0), float(0), float(0), float(0), 30)
	error_prone11 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2880), float(38), float(-4819), float(0), float(0), float(0), float(0), 30)
	error_prone12 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2895), float(35), float(-4826), float(0), float(0), float(0), float(0), 30)
	error_prone13 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2895), float(39), float(-4795), float(0), float(0), float(0), float(0), 30)
	error_prone14 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2860), float(39), float(-4827), float(0), float(0), float(0), float(0), 30)
	error_prone15 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2851), float(40), float(-4806), float(0), float(0), float(0), float(0), 30)
	error_prone16 = stcSvc.spawnObject('error-prone_battle_droid', 'tatooine', long(0), float(2871), float(38), float(-4799), float(0), float(0), float(0), float(0), 30)
	
	aiSvc.setLoiter(error_prone, float(1), float(8))  
	aiSvc.setLoiter(error_prone1, float(1), float(8)) 
	aiSvc.setLoiter(error_prone2, float(1), float(8)) 
	aiSvc.setLoiter(error_prone3, float(1), float(8)) 
	aiSvc.setLoiter(error_prone4, float(1), float(8)) 
	aiSvc.setLoiter(error_prone5, float(1), float(8)) 
	aiSvc.setLoiter(error_prone6, float(1), float(8)) 
	aiSvc.setLoiter(error_prone7, float(1), float(8)) 
	aiSvc.setLoiter(error_prone8, float(1), float(8)) 
	aiSvc.setLoiter(error_prone9, float(1), float(8)) 
	aiSvc.setLoiter(error_prone10, float(1), float(8)) 
	aiSvc.setLoiter(error_prone11, float(1), float(8)) 
	aiSvc.setLoiter(error_prone12, float(1), float(8)) 
	aiSvc.setLoiter(error_prone13, float(1), float(8)) 
	aiSvc.setLoiter(error_prone14, float(1), float(8)) 
	aiSvc.setLoiter(error_prone15, float(1), float(8)) 
	aiSvc.setLoiter(error_prone16, float(1), float(8)) 
	return
	
