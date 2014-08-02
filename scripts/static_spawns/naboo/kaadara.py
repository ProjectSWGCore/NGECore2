import sys
from resources.datatables import Options
from resources.datatables import StateStatus


def addPlanetSpawns(core, planet):
	
	stcSvc = core.staticService
	objSvc = core.objectService

	#junkdealer will be added as soon as i find coords
	stcSvc.spawnObject('junkdealer', 'naboo', long(0), float(5093.8560), float(-191.7385), float(6710.1772), float(0.7283), float(-0.6853))
	stcSvc.spawnObject('junkdealer', 'naboo', long(0), float(5060.5347), float(-191.7385), float(6710.0000), float(0.6257), float(0.7800))
	
	#Legacy NPC's
	panaka = stcSvc.spawnObject('panaka', 'naboo', long(0), float(5196.5986), float(-192.0000), float(6712.7124), float(0.9984), float(0.0572))
	
	return	
