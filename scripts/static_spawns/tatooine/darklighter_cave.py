import sys
# Project SWG:   darklighter cave
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.

	cave = core.objectService.getObject(long(-466404036409557011))
	
	lifter = stcSvc.spawnObject('object/mobile/shared_cll8_binary_load_lifter.iff', 'tatooine', cave.getCellByCellNumber(2), float(8.8), float(-21.8), float(-38.1), float(0.71), float(0), float(0.70), float(0))	
	lifter.setCustomName('a Binary Load Lifter')
	lifter.setOptionsBitmask(256)

	stcSvc.spawnObject('desert_demon_brawler', 'tatooine', cave.getCellByCellNumber(4), float(-34.7), float(-49.1), float(-109.5), float(0.71), float(0), float(0.70), float(0))
	
	stcSvc.spawnObject('desert_demon_marksman', 'tatooine', cave.getCellByCellNumber(5), float(-12.8), float(-48.4), float(-161.9), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_bodyguard', 'tatooine', cave.getCellByCellNumber(5), float(-9), float(-48.6), float(-167.8), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_brawler', 'tatooine', cave.getCellByCellNumber(5), float(-6.3), float(-48.6), float(-164.2), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_bodyguard', 'tatooine', cave.getCellByCellNumber(7), float(41), float(-71), float(-187.3), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_brawler', 'tatooine', cave.getCellByCellNumber(11), float(35.5), float(-80.8), float(-232.9), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_brawler', 'tatooine', cave.getCellByCellNumber(11), float(40), float(-80.8), float(-234.5), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_brawler', 'tatooine', cave.getCellByCellNumber(11), float(39.6), float(-80.7), float(-244.7), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_bodyguard', 'tatooine', cave.getCellByCellNumber(13), float(24.8), float(-79.7), float(-265.8), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_brawler', 'tatooine', cave.getCellByCellNumber(13), float(28.2), float(-79.7), float(-275.7), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_brawler', 'tatooine', cave.getCellByCellNumber(13), float(10.2), float(-79.7), float(-277.7), float(0.71), float(0), float(0.70), float(0))
	
	stcSvc.spawnObject('desert_demon_brawler', 'tatooine', cave.getCellByCellNumber(14), float(-25.4), float(-85.4), float(-263.9), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_bodyguard', 'tatooine', cave.getCellByCellNumber(15), float(-78.4), float(-96.6), float(-233), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_brawler', 'tatooine', cave.getCellByCellNumber(17), float(-109.5), float(-107.9), float(-338.8), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_brawler', 'tatooine', cave.getCellByCellNumber(17), float(-126.7), float(-107.8), float(-313.9), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_brawler', 'tatooine', cave.getCellByCellNumber(17), float(-150.3), float(-116.7), float(-335.7), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_bodyguard', 'tatooine', cave.getCellByCellNumber(17), float(-158.7), float(-111.9), float(-324.7), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_bodyguard', 'tatooine', cave.getCellByCellNumber(17), float(-170.9), float(-109.1), float(-317.3), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_brawler', 'tatooine', cave.getCellByCellNumber(9), float(-57.5), float(-65.2), float(-197.8), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_brawler', 'tatooine', cave.getCellByCellNumber(10), float(-138), float(-86), float(-257.5), float(0.71), float(0), float(0.70), float(0))	
	
	stcSvc.spawnObject('desert_demon_brawler', 'tatooine', cave.getCellByCellNumber(9), float(-91.3), float(-74.4), float(-199.2), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('desert_demon_brawler', 'tatooine', cave.getCellByCellNumber(10), float(-124.2), float(-84.5), float(-253.9), float(0.71), float(0), float(0.70), float(0))	
	
	#dunestalker
	
	stcSvc.spawnObject('dune_stalker_brawler', 'tatooine', cave.getCellByCellNumber(12), float(-163.8), float(-88.5), float(-221.7), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('dune_stalker_enforcer', 'tatooine', cave.getCellByCellNumber(12), float(-162.6), float(-87.5), float(-214.3), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('dune_stalker_brawler', 'tatooine', cave.getCellByCellNumber(12), float(-174.7), float(-87.8), float(-217.7), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('dune_stalker_leader', 'tatooine', cave.getCellByCellNumber(12), float(-198.4), float(-89), float(-166.5), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('dune_stalker_marksman', 'tatooine', cave.getCellByCellNumber(12), float(-210.5), float(-88), float(-174.9), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('dune_stalker_scavenger', 'tatooine', cave.getCellByCellNumber(12), float(-233.4), float(-87.4), float(-221.2), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('dune_stalker_enforcer', 'tatooine', cave.getCellByCellNumber(12), float(-249.5), float(-87.6), float(-229.4), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('dune_stalker_brawler', 'tatooine', cave.getCellByCellNumber(12), float(-254.4), float(-88.1), float(-219.7), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('dune_stalker_enforcer', 'tatooine', cave.getCellByCellNumber(12), float(-256.3), float(-84.8), float(-198.2), float(0.71), float(0), float(0.70), float(0))
				
	stcSvc.spawnObject('rock_beetle', 'tatooine', cave.getCellByCellNumber(12), float(-275.4), float(-81.7), float(-192.8), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('rock_beetle', 'tatooine', cave.getCellByCellNumber(12), float(-276), float(-81.1), float(-182), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('rock_beetle', 'tatooine', cave.getCellByCellNumber(12), float(-286), float(-81), float(-199.6), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('rock_beetle', 'tatooine', cave.getCellByCellNumber(12), float(-269.9), float(-86.1), float(-248.1), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('rock_beetle', 'tatooine', cave.getCellByCellNumber(12), float(-270.7), float(-86.1), float(-263), float(0.71), float(0), float(0.70), float(0))
	stcSvc.spawnObject('rock_beetle', 'tatooine', cave.getCellByCellNumber(12), float(-264.9), float(-84.8), float(-251.6), float(0.71), float(0), float(0.70), float(0))

	
	return
	
	
