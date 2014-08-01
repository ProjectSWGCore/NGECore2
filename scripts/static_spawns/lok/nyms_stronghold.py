import sys
# Project SWG:   Imperial Recruiters tatooine:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):
	
	stcSvc = core.staticService
	objSvc = core.objectService
	#recruiter Imp Outpost

	stcSvc.spawnObject('junkdealer', 'lok', long(0), float(366), float(9), float(5202), float(0.71), float(0), float(0.70), float(0))	

	return