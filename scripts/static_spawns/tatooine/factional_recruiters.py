import sys
# Project SWG:   Factional Recruiters tatooine:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):
	
	stcSvc = core.staticService
	objSvc = core.objectService
	
#Imperial Recruiters
	
	#Imp recruiter Eisley
	smallImpHouse = core.objectService.getObject(long(1280129))
	stcSvc.spawnObject('imperial_recruiter', 'tatooine', smallImpHouse.getCellByCellNumber(3), float(-6), float(1), float(9.2), float(0.71), float(0), float(0.70), float(0))	

	#Imp recruiter Bestine
	#Hill
	stcSvc.spawnObject('imperial_recruiter', 'tatooine', long(0), float(-1138), float(98), float(-3897), float(0.71), float(0), float(0.70), float(0))
	#outside SP
	stcSvc.spawnObject('imperial_recruiter', 'tatooine', long(0), float(-1275), float(12), float(-3594), float(0), float(0), float(0), float(0))	

#Rebel Recruiters
	
	#Anchorhead cantina
	anchCantina = core.objectService.getObject(long(1213343)) 
	stcSvc.spawnObject('rebel_recruiter', 'tatooine', anchCantina.getCellByCellNumber(6), float(-6.1), float(-1.8), float(-6.2), float(0), float(0), float(0), float(-90))		
	
	return