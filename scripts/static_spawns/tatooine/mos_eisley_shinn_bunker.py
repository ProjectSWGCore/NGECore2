import sys
# Project SWG:   Mos Eisley Shinn bunker:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	building = core.objectService.getObject(long(-466404038086555772))
	
	# Shinn Mugger outisde
	
	shinn_mugger = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3410), float(8), float(-5461), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger1 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3432), float(9), float(-5445), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger2 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3464), float(10), float(-5430), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger3 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3457), float(12), float(-5419), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger4 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3431), float(6), float(-5427), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger5 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3405), float(6), float(-5434), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger6 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3398), float(5), float(-5459), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger7 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3394), float(7), float(-5483), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger8 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3417), float(8), float(-5491), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger9 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3439), float(9), float(-5482), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger10 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3447), float(9), float(-5462), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger11 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3429), float(7), float(-5473), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger12 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3449), float(10), float(-5504), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger13 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3415), float(7), float(-5501), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger14 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3392), float(7), float(-5489), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger15 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3380), float(5), float(-5465), float(0), float(0), float(0), float(0), 30)	
	shinn_mugger16 = stcSvc.spawnObject('shinn_mugger', 'tatooine', long(0), float(3394), float(4), float(-5456), float(0), float(0), float(0), float(0), 30)												
	
	
	#shinn Guards outside bunker
	shinn_guard_o1 = stcSvc.spawnObject('shinn_guard', 'tatooine', long(0), float(3417), float(7), float(-5461), float(0), float(0), float(0), float(0), 30)													
	shinn_guard_o2 = stcSvc.spawnObject('shinn_guard', 'tatooine', long(0), float(3423), float(7), float(-5461), float(0), float(0), float(0), float(0), 30)	
													
	# Shinn Guards in Bunker
	shinn_guard = stcSvc.spawnObject('shinn_guard', 'tatooine', building.getCellByCellNumber(long(1)), float(-3.3), float(0.3), float(3.6), float(0), float(0), float(0), float(0), 30)
	shinn_guard1 = stcSvc.spawnObject('shinn_guard', 'tatooine', building.getCellByCellNumber(long(3)), float(2.9), float(0.3), float(-4.0), float(0), float(0), float(0), float(0), 30)
	shinn_guard2 = stcSvc.spawnObject('shinn_guard', 'tatooine', building.getCellByCellNumber(long(4)), float(3.8), float(-3.3), float(4.8), float(0), float(0), float(0), float(0), 30)
	shinn_guard3 = stcSvc.spawnObject('shinn_guard', 'tatooine', building.getCellByCellNumber(long(5)), float(-4.4), float(-10.3), float(-6.1), float(0), float(0), float(0), float(0), 30)
	shinn_guard4 = stcSvc.spawnObject('shinn_guard', 'tatooine', building.getCellByCellNumber(long(7)), float(5.3), float(-13.8), float(6.6), float(0), float(0), float(0), float(0), 30)
	shinn_guard5 = stcSvc.spawnObject('shinn_guard', 'tatooine', building.getCellByCellNumber(long(7)), float(-2.1), float(-13.8), float(6.8), float(0), float(0), float(0), float(0), 30)
	shinn_guard6 = stcSvc.spawnObject('shinn_guard', 'tatooine', building.getCellByCellNumber(long(7)), float(-6.8), float(-13.8), float(6.7), float(0), float(0), float(0), float(0), 30)
	shinn_guard7 = stcSvc.spawnObject('shinn_guard', 'tatooine', building.getCellByCellNumber(long(8)), float(1.5), float(-20.8), float(-3.5), float(0), float(0), float(0), float(0), 30)	
	shinn_guard8 = stcSvc.spawnObject('shinn_guard', 'tatooine', building.getCellByCellNumber(long(9)), float(-1.0), float(-20.7), float(11.0), float(0), float(0), float(0), float(0), 30)
	shinn_guard9 = stcSvc.spawnObject('shinn_guard', 'tatooine', building.getCellByCellNumber(long(9)), float(3.6), float(-20.7), float(11.4), float(0), float(0), float(0), float(0), 30)
	shinn_guard10 = stcSvc.spawnObject('shinn_guard', 'tatooine', building.getCellByCellNumber(long(9)), float(1.6), float(-20.7), float(4.7), float(0), float(0), float(0), float(0), 30)
	shinn_guard11 = stcSvc.spawnObject('shinn_guard', 'tatooine', building.getCellByCellNumber(long(10)), float(-1.6), float(-20.8), float(23.4), float(0), float(0), float(0), float(0), 30)
	shinn_guard12 = stcSvc.spawnObject('shinn_guard', 'tatooine', building.getCellByCellNumber(long(10)), float(4.6), float(-20.8), float(23.4), float(0), float(0), float(0), float(0), 30)

	#temp until cellbycellname is fixed 
	

	
	# Vaigonn Shinn				
	vaigonn_shinn = stcSvc.spawnObject('vaigon_shinn', 'tatooine', building.getCellByCellNumber(long(10)), float(1.7), float(-20.8), float(29.7), float(-1), float(0), float(0), float(0), 30)
	
	return
	
	
