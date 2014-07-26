
import sys
from resources.datatables import Options
from resources.datatables import StateStatus


def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	

	#doaba guerfel
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(3190), float(300.2), float(5387), float(0.71), float(0.71))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(3227), float(300.2), float(5387), float(0.71), float(-0.71))

	return	
