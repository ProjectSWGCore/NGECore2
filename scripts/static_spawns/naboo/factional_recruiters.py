import sys
# Project SWG:   Imperial Recruiters Naboo:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):
	
	stcSvc = core.staticService
	objSvc = core.objectService
	#recruiter theed
	stcSvc.spawnObject('object/mobile/shared_dressed_imperial_officer_m_3.iff', 'naboo', long(0), float(-4927.9), float(6), float(4230.5), float(-0.998), float(0), float(0.06), float(0))	

	#deeja Peek
	stcSvc.spawnObject('object/mobile/shared_dressed_imperial_officer_m_5.iff', 'naboo', long(0), float(5307), float(326), float(-1584), float(0), float(0), float(0), float(0))	

	#Rebrecruiter Moenia
	stcSvc.spawnObject('object/mobile/shared_dressed_rebel_recruiter_human_female_02.iff', 'naboo', long(0), float(4827), float(4), float(-4697), float(0), float(-0.707), float(0), float(0.707))		
	
	
	return