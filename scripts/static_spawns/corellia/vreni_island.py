
import sys
from resources.datatables import Options
from resources.datatables import StateStatus


def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	
	#vreni
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(-5542), float(23.4), float(-6228.7), float(0.71), float(0.71))
	stcSvc.spawnObject('junkdealer', 'corellia', long(0), float(-5552), float(16.1), float(-6071), float(0.71), float(-0.71))
	

	return	
