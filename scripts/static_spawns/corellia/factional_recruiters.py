import sys
# Project SWG:   Imperial Recruiters tatooine:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):
	
	stcSvc = core.staticService
	objSvc = core.objectService
	#recruiter Imp Outpost

	stcSvc.spawnObject('object/mobile/shared_dressed_imperial_officer_m_4.iff', 'corellia', long(0), float(6718), float(315), float(-5809), float(0), float(0), float(0), float(0))	

	return