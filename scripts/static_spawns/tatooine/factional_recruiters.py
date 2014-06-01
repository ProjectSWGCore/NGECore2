import sys
# Project SWG:   Imperial Recruiters tatooine:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):
	
	stcSvc = core.staticService
	objSvc = core.objectService
	#recruiter Eisley
	smallImpHouse = core.objectService.getObject(long(1280129))
	stcSvc.spawnObject('object/mobile/shared_dressed_imperial_officer_f.iff', 'tatooine', smallImpHouse.getCellByCellNumber(3), float(-6), float(1), float(9.2), float(0.71), float(0), float(0.70), float(0))	

	#recruiter Bestine
	#Hill
	stcSvc.spawnObject('object/mobile/shared_dressed_imperial_officer_m_5.iff', 'tatooine', long(0), float(-1138), float(98), float(-3897), float(0.71), float(0), float(0.70), float(0))
	#outside SP
	stcSvc.spawnObject('object/mobile/shared_dressed_imperial_officer_m_3.iff', 'tatooine', long(0), float(-1275), float(12), float(-3594), float(0), float(0), float(0), float(0))	

	return