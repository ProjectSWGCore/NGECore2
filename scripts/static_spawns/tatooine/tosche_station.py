import sys
from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService

	stcSvc.spawnObject('desert_demon', 'tatooine', long(0), float(-260), float(3.5), float(-6205.3), float(0.70), float(0.71), float(0), float(0), 45)
	stcSvc.spawnObject('desert_demon', 'tatooine', long(0), float(-243), float(12.3), float(-6202), float(0.70), float(0.71), float(0), float(0), 45)
	stcSvc.spawnObject('desert_demon', 'tatooine', long(0), float(-250), float(6.3), float(-6199), float(0.70), float(0.71), float(0), float(0), 45)
	stcSvc.spawnObject('desert_demon', 'tatooine', long(0), float(-255), float(4.1), float(-6207), float(0.70), float(0.71), float(0), float(0), 45)
	return
	
