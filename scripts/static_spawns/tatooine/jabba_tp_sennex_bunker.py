import sys
# Project SWG:   Jem Levar Place Spawn
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	# TODO Check all NPCs for personalized scripting, change format.
	bunker = core.objectService.getObject(long(-466404036409557025))
	
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(2), float(-4), float(0.3), float(-0.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(4), float(3.7), float(-4), float(9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(5), float(8.3), float(-12), float(26.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(5), float(-3.4), float(-12), float(30.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(5), float(-2.9), float(-12), float(47.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(5), float(7.4), float(-12), float(51.7), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(6), float(17.3), float(-14), float(61.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(7), float(17.5), float(-16), float(77), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(7), float(22.3), float(-16), float(71.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_lookout', 'tatooine', bunker.getCellByCellNumber(7), float(12.9), float(-16), float(82.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(18), float(-12.1), float(-12), float(93), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('sennex_lookout', 'tatooine', bunker.getCellByCellNumber(18), float(-17.2), float(-12), float(97.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(18), float(-7.6), float(-12), float(87.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(20), float(-41.5), float(-12), float(89.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(20), float(-40.5), float(-12), float(103.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(20), float(-31), float(-12), float(114.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(21), float(-36.6), float(-12), float(136.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(8), float(18), float(-16), float(105.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(10), float(-3.7), float(-16), float(105.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_lookout', 'tatooine', bunker.getCellByCellNumber(10), float(3.8), float(-16), float(114.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(10), float(20.2), float(-16), float(116.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(10), float(19.9), float(-16), float(126.2), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(10), float(10.4), float(-16), float(136.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_lookout', 'tatooine', bunker.getCellByCellNumber(10), float(-2.8), float(-16), float(136.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(10), float(-12.7), float(-16), float(125.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(10), float(-12.7), float(-16), float(116.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(11), float(-10.9), float(-13), float(151.3), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('sennex_lookout', 'tatooine', bunker.getCellByCellNumber(12), float(-26.2), float(-12), float(145.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(12), float(-22.3), float(-12), float(161.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(12), float(-31.4), float(-12), float(161.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(12), float(-26.8), float(-12), float(174), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(13), float(-45.4), float(-15.4), float(150.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(15), float(-67.1), float(-22.4), float(150.9), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(16), float(-80.9), float(-25.9), float(137.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(17), float(-76.3), float(-28), float(122.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(17), float(-100.6), float(-28), float(120.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(17), float(-102.7), float(-28), float(119.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(17), float(-102.3), float(-28), float(103.4), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(17), float(-76), float(-28), float(99), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(26), float(-76.6), float(-28), float(69.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(26), float(-76.1), float(-28), float(56.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(26), float(-85.5), float(-28), float(56.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(33), float(-113.2), float(-36), float(77.3), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(22), float(-16.6), float(-16), float(63.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(23), float(-43.9), float(-24), float(62.9), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(24), float(-64.1), float(-28), float(62.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(25), float(-65), float(-28), float(-78), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_hunter', 'tatooine', bunker.getCellByCellNumber(31), float(-94.4), float(-32.3), float(42.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_guard', 'tatooine', bunker.getCellByCellNumber(31), float(-95), float(-40.3), float(-18.4), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('sennex_slavemaster', 'tatooine', bunker.getCellByCellNumber(32), float(-57.1), float(-44), float(-22.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_slavemaster', 'tatooine', bunker.getCellByCellNumber(32), float(-57.6), float(-44), float(4.8), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('sennex_slaver', 'tatooine', bunker.getCellByCellNumber(32), float(-80.2), float(-44), float(-8.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_slavemaster', 'tatooine', bunker.getCellByCellNumber(32), float(-100.2), float(-44), float(-20.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_slaver', 'tatooine', bunker.getCellByCellNumber(32), float(-100.2), float(-44), float(4.4), float(0), float(0), float(0), float(0), 45)	

	stcSvc.spawnObject('sennex_slavemaster', 'tatooine', bunker.getCellByCellNumber(29), float(-146.1), float(-44), float(76.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_slaver', 'tatooine', bunker.getCellByCellNumber(29), float(-146.3), float(-44), float(81), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_slavemaster', 'tatooine', bunker.getCellByCellNumber(29), float(-141.1), float(-44), float(96.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_slavemaster', 'tatooine', bunker.getCellByCellNumber(29), float(-153.1), float(-44), float(97.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('sennex_slavemaster', 'tatooine', bunker.getCellByCellNumber(29), float(-164.6), float(-44), float(74.5), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('sennex_slavemaster', 'tatooine', bunker.getCellByCellNumber(29), float(-165.9), float(-44), float(92.4), float(0), float(0), float(0), float(0), 45)
	
	return
	
