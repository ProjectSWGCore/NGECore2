import sys
# Project SWG:   Imperial Recruiters dantooine:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):
	
	stcSvc = core.staticService
	objSvc = core.objectService
	#imprecruiter Imp Outpost

	stcSvc.spawnObject('imperial_recruiter', 'dantooine', long(0), float(-4198), float(3), float(-2411), float(0.819), float(-0.572))
	 
	#rebrecruiter abandoned base
	stcSvc.spawnObject('rebel_recruiter', 'dantooine', long(0), float(-6817), float(46), float(5511), float(0), float(-0.707), float(0), float(0.707))		
	

	return