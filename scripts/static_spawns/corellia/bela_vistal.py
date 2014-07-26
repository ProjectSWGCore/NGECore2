
import sys
from resources.datatables import Options
from resources.datatables import StateStatus


def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	
	#junkdealer 
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(6840), float(315), float(-5630), float(0.707), float(-0.707))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(6852), float(315), float(-5802), float(0.71), float(-0.71))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(6756), float(315), float(-5778), float(0.71), float(0.71))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(6970), float(330), float(-5588), float(0.71), float(0.71))
	return	
