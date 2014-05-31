import sys

def addZones(core):
	
	gcwService = core.gcwService
	
	gcwService.addPvPZone('rori', 5318, 5680, 400)
	gcwService.addPvPZone('corellia', 4722, -5233, 200)	
	gcwService.addPvPZone('naboo', 1019, -1508, 150)	
	gcwService.addPvPZone('talus', -4899, -3137, 150)		