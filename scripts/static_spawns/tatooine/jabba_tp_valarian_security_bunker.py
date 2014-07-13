import sys
# Project SWG:   Jabba TP Valarian Security Bunker:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	stcSvc.spawnObject('tatooine_valarian_enforcer', 'tatooine', long(0), float(-3941.2), float(4.9), float(-3994), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', long(0), float(-3935.9), float(6.5), float(-4012.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', long(0), float(-3937.7), float(5.4), float(-3983.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_enforcer', 'tatooine', long(0), float(-3952.5), float(6.8), float(-4015.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', long(0), float(-3954.1), float(6.7), float(-4019.8), float(0), float(0), float(0), float(0), 45)	


	bunker = core.objectService.getObject(long(-466404037065936405))
	
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(5), float(3.1), float(-12), float(31.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(5), float(24.9), float(-12), float(46.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(5), float(4), float(-12), float(54.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(5), float(-15.4), float(-12), float(47.1), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(7), float(-50.4), float(-20), float(38.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_enforcer', 'tatooine', bunker.getCellByCellNumber(7), float(-57), float(-20), float(36.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(7), float(-59.6), float(-20), float(44.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_enforcer', 'tatooine', bunker.getCellByCellNumber(7), float(-50.7), float(-20), float(66.6), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('tatooine_valarian_executioner', 'tatooine', bunker.getCellByCellNumber(8), float(-70.5), float(-20), float(14.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('jabbas_palace_iris_sinclair', 'tatooine', bunker.getCellByCellNumber(8), float(-49.8), float(-20), float(13.1), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('tatooine_valarian_executioner', 'tatooine', bunker.getCellByCellNumber(9), float(-70.3), float(-20), float(77.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('jabbas_palace_fnast_drexler', 'tatooine', bunker.getCellByCellNumber(9), float(-49), float(-20), float(82.5), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(10), float(35.7), float(-12), float(34.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(10), float(36.2), float(-12), float(77.1), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('tatooine_valarian_executioner', 'tatooine', bunker.getCellByCellNumber(12), float(72.5), float(-12), float(58.6), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('tatooine_valarian_executioner', 'tatooine', bunker.getCellByCellNumber(11), float(49.8), float(-12), float(83), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('jabbas_palace_arkahn_greystar', 'tatooine', bunker.getCellByCellNumber(11), float(71.6), float(-12), float(83.2), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('tatooine_valarian_executioner', 'tatooine', bunker.getCellByCellNumber(13), float(36.4), float(-20), float(123.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_executioner', 'tatooine', bunker.getCellByCellNumber(13), float(19), float(-20), float(122.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('jabbas_palace_emanon', 'tatooine', bunker.getCellByCellNumber(13), float(22.1), float(-20), float(139.4), float(0), float(0), float(0), float(0), 45)

	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(21), float(-8.2), float(-20), float(66.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(21), float(-2.3), float(-20), float(77), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('tatooine_valarian_executioner', 'tatooine', bunker.getCellByCellNumber(19), float(-23), float(-20), float(100.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('jabbas_palace_careem', 'tatooine', bunker.getCellByCellNumber(19), float(-22.4), float(-20), float(117.5), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(18), float(-30.7), float(-20), float(49.5), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(16), float(-15.5), float(-20), float(2.9), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(14), float(21.1), float(-12), float(-1.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(14), float(51.6), float(-12), float(24.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(14), float(58.7), float(-12), float(20.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('tatooine_valarian_vandal', 'tatooine', bunker.getCellByCellNumber(14), float(55.2), float(-12), float(16.9), float(0), float(0), float(0), float(0), 45)
	return
	
