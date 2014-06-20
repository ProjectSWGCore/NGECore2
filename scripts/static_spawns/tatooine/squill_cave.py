import sys
# Project SWG:   Anchorhead:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	cave = core.objectService.getObject(long(-466404040697398729))
	
	
	stcSvc.spawnObject('mountain_squill_guardian', 'tatooine', cave.getCellByCellNumber(1), float(1.2), float(-21.3), float(-17.6), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(19), float(17.6), float(-24.5), float(-29.8), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(19), float(16.5), float(-31.4), float(-49.9), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_guardian', 'tatooine', cave.getCellByCellNumber(20), float(15.5), float(-35.3), float(-77.8), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(20), float(-9.4), float(-35.2), float(-75.3), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(21), float(-31.2), float(-35.6), float(-66.3), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('young_mountain_squill', 'tatooine', cave.getCellByCellNumber(22), float(-15.3), float(-44.1), float(-103.3), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(22), float(-46.4), float(-47.3), float(-99.6), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_hunter', 'tatooine', cave.getCellByCellNumber(22), float(-53.4), float(-49.8), float(-111.1), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(9), float(-80.9), float(-52.9), float(-113.3), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_guardian', 'tatooine', cave.getCellByCellNumber(9), float(-107.2), float(-54.2), float(-112.5), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(23), float(-130.9), float(-58.4), float(-128.3), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_hunter', 'tatooine', cave.getCellByCellNumber(24), float(-106.3), float(-67), float(-136.5), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(9), float(-106.3), float(-69.9), float(-112.3), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(9), float(-78.3), float(-71.6), float(-114.8), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(9), float(-91.3), float(-71.4), float(-105.5), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(25), float(-75.7), float(-52.9), float(-136.6), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(28), float(-79.8), float(-65.6), float(-157.7), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('young_mountain_squill', 'tatooine', cave.getCellByCellNumber(28), float(-53.3), float(-66.3), float(-156.4), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(28), float(-45.7), float(-66.5), float(-176.4), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(28), float(-79.1), float(-66.8), float(-191.6), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(28), float(-56), float(-66.1), float(-199.5), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_hunter', 'tatooine', cave.getCellByCellNumber(29), float(-55.4), float(-65.5), float(-228.9), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(29), float(-64.7), float(-65), float(-231.3), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(25), float(-49.5), float(-69.6), float(-124.5), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_hunter', 'tatooine', cave.getCellByCellNumber(22), float(-34.9), float(-68.1), float(-112.6), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(26), float(-8.4), float(-69.1), float(-108.1), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(26), float(3.6), float(-68), float(-90.4), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(20), float(-1.9), float(-67.4), float(-65.5), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(20), float(9.1), float(-67.4), float(-66.7), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_hunter', 'tatooine', cave.getCellByCellNumber(22), float(-25.6), float(-43.2), float(-91.4), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(9), float(-107.3), float(-69.5), float(-104.5), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_hunter', 'tatooine', cave.getCellByCellNumber(2), float(-30.5), float(-30.7), float(-20.9), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(2), float(-15.9), float(-29.3), float(-33.9), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(2), float(-13.7), float(-30.4), float(-46.3), float(-0.75), float(0), float(-0.65), float(0), 60)	
	stcSvc.spawnObject('mountain_squill_guardian', 'tatooine', cave.getCellByCellNumber(3), float(-35.1), float(-29.8), float(-49.4), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(3), float(-50.7), float(-30), float(-41.6), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_guardian', 'tatooine', cave.getCellByCellNumber(5), float(-71.8), float(-30.1), float(-40.4), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(5), float(-64.9), float(-31.4), float(-59.1), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(5), float(-69.1), float(-30.9), float(-72.2), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(7), float(-72.5), float(-36.4), float(-93.9), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('young_mountain_squill', 'tatooine', cave.getCellByCellNumber(9), float(-94.5), float(-37.8), float(-122.2), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(9), float(-105), float(-38.8), float(-107.1), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(11), float(-105.1), float(-38.9), float(-82.6), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(11), float(-125.5), float(-37.8), float(-79.1), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_hunter', 'tatooine', cave.getCellByCellNumber(6), float(-105.1), float(-36.1), float(-64.8), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(16), float(-103.7), float(-42.1), float(-30.1), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(17), float(-117), float(-45.6), float(-8.9), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_hunter', 'tatooine', cave.getCellByCellNumber(18), float(-133.8), float(-57.5), float(-14.5), float(-0.75), float(0), float(-0.65), float(0), 60) ### test###
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(16), float(-107.2), float(-58), float(-27), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(16), float(-115.2), float(-58.2), float(-33.9), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_hunter', 'tatooine', cave.getCellByCellNumber(14), float(-80.3), float(-58.1), float(-40.4), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(14), float(-80.4), float(-61.3), float(-64.9), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(14), float(-87.6), float(-56.5), float(-80.9), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(11), float(-109.1), float(-54.5), float(-83.1), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_hunter', 'tatooine', cave.getCellByCellNumber(11), float(-122.1), float(-54.3), float(-89.2), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(11), float(-133), float(-53.9), float(-79.7), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(11), float(-142.1), float(-54), float(-91.1), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(12), float(-143.7), float(-53.5), float(-116.2), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_hunter', 'tatooine', cave.getCellByCellNumber(12), float(-169.4), float(-54.8), float(-118.6), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(12), float(-171.5), float(-52.4), float(-102.4), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(10), float(-169.3), float(-49.7), float(-83.5), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(10), float(-167.3), float(-49.9), float(-71.2), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('young_mountain_squill', 'tatooine', cave.getCellByCellNumber(10), float(-168.7), float(-49.5), float(-54.8), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(8), float(-160.3), float(-50.3), float(-47), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(8), float(-139.7), float(-49.8), float(-46.1), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(6), float(-132.6), float(-49.8), float(-60.8), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_hunter', 'tatooine', cave.getCellByCellNumber(6), float(-111.9), float(-51), float(-60.3), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill_hunter', 'tatooine', cave.getCellByCellNumber(6), float(-88.9), float(-49.4), float(-61), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(5), float(-74.5), float(-47.3), float(-71.7), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(5), float(-79.3), float(-46.8), float(-42.5), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('young_mountain_squill', 'tatooine', cave.getCellByCellNumber(4), float(-66), float(-45.8), float(-25.9), float(-0.75), float(0), float(-0.65), float(0), 60)
	stcSvc.spawnObject('mountain_squill', 'tatooine', cave.getCellByCellNumber(4), float(-59.9), float(-43.2), float(-15.4), float(-0.75), float(0), float(-0.65), float(0), 60)

	
	# Hermit
	hermit = stcSvc.spawnObject('object/mobile/shared_dressed_hermit_of_tatooine.iff', 'tatooine', cave.getCellByCellNumber(27), float(38.2), float(-69.1), float(-103.1), float(-0.75), float(0), float(-0.65), float(0))
	#hermit.setCustomName('quest_hero_of_tatooine_hermit')
	hermit.setCustomName('a Hermit')
	hermit.setOptionsBitmask(256)
		
	

	return
	
