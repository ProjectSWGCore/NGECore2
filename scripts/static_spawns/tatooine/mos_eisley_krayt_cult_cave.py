import sys
# Project SWG:   Mos Eisley Krayt cult cave:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	cave = core.objectService.getObject(long(-466404036409557297))
	
	# novice
	
	novice = stcSvc.spawnObject('krayt_cult_novice', 'tatooine', cave.getCellByCellNumber(2), float(2.1), float(-11.2), float(-18.6), float(0), float(0), float(0), float(0), 30)
	novice1 = stcSvc.spawnObject('krayt_cult_novice', 'tatooine', cave.getCellByCellNumber(4), float(-14.1), float(-53.8), float(-122.7), float(0), float(0), float(0), float(0), 30)
	novice2 = stcSvc.spawnObject('krayt_cult_novice', 'tatooine', cave.getCellByCellNumber(5), float(-73.3), float(-56), float(-183.6), float(0), float(0), float(0), float(0), 30)
	novice3 = stcSvc.spawnObject('krayt_cult_novice', 'tatooine', cave.getCellByCellNumber(12), float(-72.2), float(-70.9), float(-255.1), float(0), float(0), float(0), float(0), 30)
	novice4 = stcSvc.spawnObject('krayt_cult_novice', 'tatooine', cave.getCellByCellNumber(7), float(41.2), float(-71.9), float(-228.1), float(0), float(0), float(0), float(0), 30)
	novice5 = stcSvc.spawnObject('krayt_cult_novice', 'tatooine', cave.getCellByCellNumber(8), float(98.7), float(-89.7), float(-285.8), float(0), float(0), float(0), float(0), 30)
	novice6 = stcSvc.spawnObject('krayt_cult_novice', 'tatooine', cave.getCellByCellNumber(15), float(112.9), float(-91.3), float(-331.9), float(0), float(0), float(0), float(0), 30)
	novice7 = stcSvc.spawnObject('krayt_cult_novice', 'tatooine', cave.getCellByCellNumber(20), float(-12.2), float(-120), float(-374.3), float(0), float(0), float(0), float(0), 30)
	
	
	zealot = stcSvc.spawnObject('krayt_cult_zealot', 'tatooine', cave.getCellByCellNumber(3), float(-19.2), float(-26.7), float(-42.2), float(0), float(0), float(0), float(0), 30)
	zealot1 = stcSvc.spawnObject('krayt_cult_zealot', 'tatooine', cave.getCellByCellNumber(5), float(-73.3), float(-56), float(-177.4), float(0), float(0), float(0), float(0), 30)
	zealot2 = stcSvc.spawnObject('krayt_cult_zealot', 'tatooine', cave.getCellByCellNumber(5), float(-40.8), float(-56.4), float(-208.2), float(0), float(0), float(0), float(0), 30)
	zealot3 = stcSvc.spawnObject('krayt_cult_zealot', 'tatooine', cave.getCellByCellNumber(13), float(-105.8), float(-80), float(-278.6), float(0), float(0), float(0), float(0), 30)
	zealot4 = stcSvc.spawnObject('krayt_cult_zealot', 'tatooine', cave.getCellByCellNumber(17), float(-103.3), float(-83.6), float(-327.1), float(0), float(0), float(0), float(0), 30)
	zealot5 = stcSvc.spawnObject('krayt_cult_zealot', 'tatooine', cave.getCellByCellNumber(5), float(-7.9), float(-49), float(-155.9), float(0), float(0), float(0), float(0), 30)
	zealot6 = stcSvc.spawnObject('krayt_cult_zealot', 'tatooine', cave.getCellByCellNumber(15), float(113.9), float(-90.5), float(-340.2), float(0), float(0), float(0), float(0), 30)
	zealot7 = stcSvc.spawnObject('krayt_cult_zealot', 'tatooine', cave.getCellByCellNumber(20), float(4), float(-119.4), float(-359.8), float(0), float(0), float(0), float(0), 30)
	
	
	alcolyte = stcSvc.spawnObject('krayt_cult_acolyte', 'tatooine', cave.getCellByCellNumber(3), float(-27.1), float(-40.5), float(-73.6), float(0), float(0), float(0), float(0), 30)		
	alcolyte1 = stcSvc.spawnObject('krayt_cult_acolyte', 'tatooine', cave.getCellByCellNumber(12), float(-72.2), float(-70.9), float(-255.1), float(0), float(0), float(0), float(0), 30)		
	alcolyte2 = stcSvc.spawnObject('krayt_cult_acolyte', 'tatooine', cave.getCellByCellNumber(17), float(-128.9), float(-85), float(-338.2), float(0), float(0), float(0), float(0), 30)		
	alcolyte3 = stcSvc.spawnObject('krayt_cult_acolyte', 'tatooine', cave.getCellByCellNumber(5), float(-29.4), float(-53.8), float(-177), float(0), float(0), float(0), float(0), 30)		
	alcolyte4 = stcSvc.spawnObject('krayt_cult_acolyte', 'tatooine', cave.getCellByCellNumber(15), float(106.6), float(-90.5), float(-335), float(0), float(0), float(0), float(0), 30)		
	alcolyte5 = stcSvc.spawnObject('krayt_cult_acolyte', 'tatooine', cave.getCellByCellNumber(15), float(91.4), float(-90.4), float(-326.6), float(0), float(0), float(0), float(0), 30)		
	alcolyte6 = stcSvc.spawnObject('krayt_cult_acolyte', 'tatooine', cave.getCellByCellNumber(19), float(59.2), float(-111.6), float(-358.9), float(0), float(0), float(0), float(0), 30)		
	alcolyte7 = stcSvc.spawnObject('krayt_cult_acolyte', 'tatooine', cave.getCellByCellNumber(20), float(-3.1), float(-120.7), float(-371.2), float(0), float(0), float(0), float(0), 30)		
	
	ministrant = stcSvc.spawnObject('krayt_cult_ministrant', 'tatooine', cave.getCellByCellNumber(17), float(-125.2), float(-84.6), float(-342.2), float(0), float(0), float(0), float(0), 30)
	ministrant1 = stcSvc.spawnObject('krayt_cult_ministrant', 'tatooine', cave.getCellByCellNumber(17), float(-100.9), float(-83), float(-343.2), float(0), float(0), float(0), float(0), 30)
	ministrant2 = stcSvc.spawnObject('krayt_cult_ministrant', 'tatooine', cave.getCellByCellNumber(16), float(-17.8), float(-78.3), float(-347.7), float(0), float(0), float(0), float(0), 30)
	ministrant3 = stcSvc.spawnObject('krayt_cult_ministrant', 'tatooine', cave.getCellByCellNumber(16), float(-20.5), float(-78.6), float(-345.5), float(0), float(0), float(0), float(0), 30)
	ministrant4 = stcSvc.spawnObject('krayt_cult_ministrant', 'tatooine', cave.getCellByCellNumber(16), float(-23.9), float(-78.7), float(-348.4), float(0), float(0), float(0), float(0), 30)
	ministrant5 = stcSvc.spawnObject('krayt_cult_ministrant', 'tatooine', cave.getCellByCellNumber(5), float(3.9), float(-49.1), float(-198), float(0), float(0), float(0), float(0), 30)
	ministrant6 = stcSvc.spawnObject('krayt_cult_ministrant', 'tatooine', cave.getCellByCellNumber(20), float(2.2), float(-119.1), float(-381.1), float(0), float(0), float(0), float(0), 30)
	
	monk = stcSvc.spawnObject('krayt_cult_monk', 'tatooine', cave.getCellByCellNumber(16), float(-20.2), float(-78.3), float(-349.3), float(0), float(0), float(0), float(0), 30)
	monk1 = stcSvc.spawnObject('krayt_cult_monk', 'tatooine', cave.getCellByCellNumber(16), float(-20.2), float(-78.3), float(-349.3), float(0), float(0), float(0), float(0), 30)
	monk2 = stcSvc.spawnObject('krayt_cult_monk', 'tatooine', cave.getCellByCellNumber(16), float(-20.2), float(-78.3), float(-349.3), float(0), float(0), float(0), float(0), 30)
	monk3 = stcSvc.spawnObject('krayt_cult_monk', 'tatooine', cave.getCellByCellNumber(16), float(-20.2), float(-78.3), float(-349.3), float(0), float(0), float(0), float(0), 30)
	
	bonegnasher = stcSvc.spawnObject('bone_gnasher', 'tatooine', cave.getCellByCellNumber(20), float(-11.2), float(-118.9), float(-385.8), float(0), float(0), float(0), float(0), 30)
	bonegnasher1 = stcSvc.spawnObject('bone_gnasher', 'tatooine', cave.getCellByCellNumber(20), float(-5.2), float(-118.8), float(-351.2), float(0), float(0), float(0), float(0), 30)
	bonegnasher2 = stcSvc.spawnObject('bone_gnasher', 'tatooine', cave.getCellByCellNumber(20), float(-18), float(-119.5), float(-365.1), float(0), float(0), float(0), float(0), 30)
	bonegnasher3 = stcSvc.spawnObject('bone_gnasher', 'tatooine', cave.getCellByCellNumber(15), float(101.9), float(-90.4), float(-331.9), float(0), float(0), float(0), float(0), 30)
	bonegnasher4 = stcSvc.spawnObject('bone_gnasher', 'tatooine', cave.getCellByCellNumber(5), float(-13.6), float(-51.9), float(-180), float(0), float(0), float(0), float(0), 30)
	bonegnasher5 = stcSvc.spawnObject('bone_gnasher', 'tatooine', cave.getCellByCellNumber(5), float(-58.9), float(-55.6), float(-198.4), float(0), float(0), float(0), float(0), 30)
	bonegnasher6 = stcSvc.spawnObject('bone_gnasher', 'tatooine', cave.getCellByCellNumber(5), float(-54.1), float(-56.3), float(-177), float(0), float(0), float(0), float(0), 30)
	
	
	return
	
