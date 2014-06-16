import sys
# Project SWG:   Lars Homestead:  Static Spawns
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
	
	#droids
	
	stcSvc.spawnObject('modified_battle_droid', 'tatooine', long(0), float(-2578), float(0), float(-5638), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('modified_battle_droid', 'tatooine', long(0), float(-2588), float(0), float(-5621), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('modified_battle_droid', 'tatooine', long(0), float(-2604), float(0), float(-5648), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('modified_battle_droid', 'tatooine', long(0), float(-2570), float(0), float(-5668), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('modified_battle_droid', 'tatooine', long(0), float(-2545), float(0), float(-5648), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('modified_battle_droid', 'tatooine', long(0), float(-2563), float(0), float(-5630), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('modified_battle_droid', 'tatooine', long(0), float(-2590), float(0), float(-5627), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('modified_battle_droid', 'tatooine', long(0), float(-2594), float(0), float(-5649), float(0), float(0), float(0), float(0), 45)
	return
	
