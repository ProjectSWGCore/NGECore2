import sys
# Project SWG:   Anchorhead:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	cantina = core.objectService.getObject(long(1213343))
	
	# Cantina Interior
	borraSetas = stcSvc.spawnObject('borrasetas', 'tatooine', cantina.getCellByCellNumber(2), float(9.8), float(0.4), float(-1.2), float(0.75), float(0), float(-0.65), float(0))
	
	# Outside
	aaphKoden = stcSvc.spawnObject('aaphkoden', 'tatooine', long(0), float(129), float(5.0), float(-5399), float(-0.67), float(0.73))
	
	Alger = stcSvc.spawnObject('alger', 'tatooine', long(0), float(107), float(5.0), float(-5315), float(0.96), float(0.26))
	
	carhlaBastra = stcSvc.spawnObject('carlhabastra', 'tatooine', long(0), float(128), float(5.0), float(-5428), float(-0.26), float(0.96))
	
	cuanTalon = stcSvc.spawnObject('cuantalon', 'tatooine', long(0), float(-161.7), float(65.0), float(-5322.8), float(0), float(0))
	
	dromaOrdo = stcSvc.spawnObject('dromaordo', 'tatooine', long(0), float(110), float(52.0), float(-5431), float(0.36), float(0.93))

	Sorna = stcSvc.spawnObject('sorna', 'tatooine', long(0), float(-135), float(52.0), float(-5331), float(0.36), float(0.93))

	#Junk Dealer
	stcSvc.spawnObject('junkdealer', 'tatooine', long(0), float(110), float(52), float(-5428), float(0.71), float(0.71))

	return