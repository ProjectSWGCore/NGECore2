
import sys
from resources.datatables import Options
from resources.datatables import StateStatus


def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	
	#korvella
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(-3151), float(31), float(2790), float(0.71), float(0.71))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(-3567), float(86), float(3143), float(0.71), float(-0.71))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(-3788), float(86), float(-3190), float(0), float(0))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(-3390), float(86), float(-3288), float(0), float(1))

	return	
