import sys
# Project SWG:   Mos Entha:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	#Outside
	zefAndo = stcSvc.spawnObject('object/mobile/shared_dressed_legacy_zef.iff', 'tatooine', long(0), float(-2574.9), float(0), float(-5516.7), float(0), float(0))
	zefAndo.setCustomName2('Zef Ando')
	zefAndo.setOptionsBitmask(256)
	
	
	return
	
