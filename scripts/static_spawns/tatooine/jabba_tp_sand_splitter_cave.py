import sys
# Project SWG:   Jabba TP sand splitter cave:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	cave = core.objectService.getObject(long(-466404038393479071))
	
	stcSvc.spawnObject('sand_splitter_knave', 'tatooine', cave.getCellByCellNumber(1), float(42.6), float(-23.3), float(4.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_knave', 'tatooine', cave.getCellByCellNumber(2), float(41.5), float(-34.4), float(-21.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_ruffian', 'tatooine', cave.getCellByCellNumber(2), float(40), float(-37.9), float(-52.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_knave', 'tatooine', cave.getCellByCellNumber(2), float(69.5), float(-43.9), float(-53.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_brute', 'tatooine', cave.getCellByCellNumber(10), float(68), float(-47.5), float(-72.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_brute', 'tatooine', cave.getCellByCellNumber(10), float(77.5), float(-43.7), float(-93.8), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('sand_splitter_knave', 'tatooine', cave.getCellByCellNumber(5), float(110.2), float(-42.3), float(-103.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_ruffian', 'tatooine', cave.getCellByCellNumber(5), float(111.9), float(-41.4), float(-86.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_knave', 'tatooine', cave.getCellByCellNumber(5), float(95.9), float(-42.3), float(-94.7), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('sand_splitter_ruffian', 'tatooine', cave.getCellByCellNumber(2), float(68.9), float(-44.7), float(-11.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_knave', 'tatooine', cave.getCellByCellNumber(2), float(61.8), float(-42.8), float(5.7), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('sand_splitter_knave', 'tatooine', cave.getCellByCellNumber(2), float(74.7), float(-63.9), float(-22.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_knave', 'tatooine', cave.getCellByCellNumber(3), float(94.5), float(-57.3), float(1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_ruffian', 'tatooine', cave.getCellByCellNumber(3), float(106), float(-61.2), float(-18.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_brute', 'tatooine', cave.getCellByCellNumber(3), float(94.6), float(-66.9), float(-33.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_knave', 'tatooine', cave.getCellByCellNumber(4), float(107.9), float(-72.4), float(-49.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_knave', 'tatooine', cave.getCellByCellNumber(4), float(80.5), float(-72.4), float(-65.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_ruffian', 'tatooine', cave.getCellByCellNumber(4), float(96.9), float(-72.5), float(-76.4), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('sand_splitter_knave', 'tatooine', cave.getCellByCellNumber(6), float(71.8), float(-69.4), float(-94.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_knave', 'tatooine', cave.getCellByCellNumber(6), float(83), float(-62.3), float(122.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_brute', 'tatooine', cave.getCellByCellNumber(7), float(106.5), float(-62.8), float(-124.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_knave', 'tatooine', cave.getCellByCellNumber(5), float(112.1), float(-62.2), float(-92.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_brute', 'tatooine', cave.getCellByCellNumber(8), float(132.8), float(-62.6), float(-79.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_ruffian', 'tatooine', cave.getCellByCellNumber(8), float(132.3), float(-62), float(-114.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sand_splitter_knave', 'tatooine', cave.getCellByCellNumber(8), float(165.5), float(-62.8), float(-105.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('saul_rouse', 'tatooine', cave.getCellByCellNumber(8), float(156.2), float(-62.6), float(-73.6), float(0), float(0), float(0), float(0), 45)#saul
	stcSvc.spawnObject('sand_splitter_ruffian', 'tatooine', cave.getCellByCellNumber(9), float(186.3), float(-62.1), float(-84.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('erib_kurrugh', 'tatooine', cave.getCellByCellNumber(9), float(205.6), float(-61.9), float(-87.4), float(0), float(0), float(0), float(0), 45) #erib
	return
	
	
