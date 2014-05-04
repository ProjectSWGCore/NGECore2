import sys
# Project SWG:   Mos Eisley:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	rebRecruiter = stcSvc.spawnObject('object/mobile/shared_dressed_rebel_recruiter_human_female_01.iff', 'tatooine', long(1213345), float(-2.2), float(0.4), float(-5.4), float(0), float(0))	
	rebRecruiter.setOptionsBitmask(264)

	return
	
