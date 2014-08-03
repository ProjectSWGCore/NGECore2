import sys
# Project SWG:   Imperial Recruiters tatooine:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):
	
	stcSvc = core.staticService
	objSvc = core.objectService
	#recruiter Imp Outpost

	stcSvc.spawnObject('imperial_recruiter', 'corellia', long(0), float(6718), float(315), float(-5809), float(0), float(0), float(0), float(0))	

	#rebrecruiter tyrena
	tyrenaCantina = core.objectService.getObject(long(2625352))
	stcSvc.spawnObject('rebel_recruiter', 'corellia', tyrenaCantina.getCellByCellNumber(11), float(-25.8), float(-0.9), float(0.4), float(0), float(-0.707), float(0), float(0.707))		
	
	#cnet cantina
	cnetCantina = core.objectService.getObject(long(8105493))
	stcSvc.spawnObject('rebel_recruiter', 'corellia', cnetCantina.getCellByCellNumber(11), float(-25.8), float(-0.9), float(0.4), float(0), float(-0.707), float(0), float(0.707))		
	

	return