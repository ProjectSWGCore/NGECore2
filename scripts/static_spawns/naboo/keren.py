import sys
from resources.datatables import Options
from resources.datatables import StateStatus


def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	
	#junkdealer 
	stcSvc.spawnObject('junkdealer', 'naboo', long(0), float(1242), float(13), float(2732), float(0.707), float(0.707))
	stcSvc.spawnObject('junkdealer', 'naboo', long(0), float(1774), float(12.5), float(2625), float(0.707), float(0.707))
	
	
	return	
