import sys
from resources.datatables import Options
from resources.datatables import StateStatus


def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	
	#junkdealer 
	stcSvc.spawnObject('junkdealer', 'naboo', long(0), float(5135), float(346.5), float(-1514), float(0.707), float(0.707))
	
	
	return	
