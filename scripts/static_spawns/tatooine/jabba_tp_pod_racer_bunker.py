import sys
# Project SWG:   Jabba TP Pod Racer Bunker:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-714.5), float(11), float(-6281), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-708.6), float(11.9), float(-6268.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-691.2), float(12.2), float(-6269.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-673.8), float(12.9), float(-6271.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-673.5), float(11.9), float(-6297.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-653.3), float(14), float(-6307.1), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-650.5), float(14.8), float(-6318.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-659.3), float(14.1), float(-6321.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-684.3), float(11.8), float(-6309.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-697.4), float(11.4), float(-6305.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-706.8), float(11.6), float(-6311.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-727.2), float(12.1), float(-6312.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-728.5), float(9), float(-6337.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-733.6), float(14.1), float(-6347.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-724.7), float(10.7), float(-6294.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-728.2), float(10.6), float(-6280), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-734.1), float(10.9), float(-6270.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_pod_racer', 'tatooine', long(0), float(-728.1), float(11), float(-6255.1), float(0), float(0), float(0), float(0), 45)
	
	bunker = core.objectService.getObject(long(-466404037726719318))
	
	stcSvc.spawnObject('valarian_mechanic', 'tatooine', bunker.getCellByCellNumber(2), float(-4.1), float(0.3), float(1.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_mechanic', 'tatooine', bunker.getCellByCellNumber(3), float(4.1), float(0.3), float(-3.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_mechanic', 'tatooine', bunker.getCellByCellNumber(4), float(3.2), float(-12), float(26.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_crew_chief', 'tatooine', bunker.getCellByCellNumber(4), float(14.1), float(-12), float(31.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_crew_chief', 'tatooine', bunker.getCellByCellNumber(5), float(29.4), float(-12), float(27.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_crew_chief', 'tatooine', bunker.getCellByCellNumber(5), float(29.5), float(-12), float(35.3), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_mechanic', 'tatooine', bunker.getCellByCellNumber(7), float(8.5), float(-14), float(59.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_crew_chief', 'tatooine', bunker.getCellByCellNumber(7), float(-3), float(-16), float(67.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_crew_chief', 'tatooine', bunker.getCellByCellNumber(7), float(0.6), float(-16), float(84), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_mechanic', 'tatooine', bunker.getCellByCellNumber(7), float(20.9), float(-16), float(78.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_mechanic', 'tatooine', bunker.getCellByCellNumber(7), float(28), float(-16), float(78.8), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_crew_chief', 'tatooine', bunker.getCellByCellNumber(9), float(-42.2), float(-14), float(74.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_mechanic', 'tatooine', bunker.getCellByCellNumber(9), float(-36.5), float(-14), float(84.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_mechanic', 'tatooine', bunker.getCellByCellNumber(8), float(54.8), float(-16), float(77.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('doane_watki', 'tatooine', bunker.getCellByCellNumber(8), float(80.6), float(-16), float(79.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_mechanic', 'tatooine', bunker.getCellByCellNumber(8), float(78.3), float(-16), float(62.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_mechanic', 'tatooine', bunker.getCellByCellNumber(8), float(61.2), float(-16), float(58), float(0), float(0), float(0), float(0), 45)

	return
	
