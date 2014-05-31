import sys
# Project SWG:   Imperial Recruiters talus:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):
	
	stcSvc = core.staticService
	objSvc = core.objectService
	#recruiter Imp Outpost
	stcSvc.spawnObject('object/mobile/shared_dressed_imperial_officer_m_6.iff', 'talus', long(0), float(-2192), float(20), float(2269), float(0), float(0), float(0), float(0))	

	return