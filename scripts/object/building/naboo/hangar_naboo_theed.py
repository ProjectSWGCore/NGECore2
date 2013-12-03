import sys

def setup(core, object):
	stcSvc = core.staticService
	cell = object.getCellByCellNumber(3)
	cell2 = object.getCellByCellNumber(4)
	cell3 = object.getCellByCellNumber(5)
	
	if cell: 
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', object.getPlanet().getName(), cell.getObjectID(), float(-26.94), float(0.749), float(-67.14), float(1), float(0))
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', object.getPlanet().getName(), cell2.getObjectID(), float(26.94), float(0.749), float(-67.14), float(1), float(0)) 
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', object.getPlanet().getName(), cell3.getObjectID(), float(-9.49), float(7.979), float(-45.2359), float(0), float(1)) 
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', object.getPlanet().getName(), cell3.getObjectID(), float(9.49), float(7.979), float(-45.2359), float(0), float(1))
		stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', object.getPlanet().getName(), cell3.getObjectID(), float(-10), float(7.979), float(10), float(-0.707107), float(0.707107)) 
		stcSvc.spawnObject('object/creature/npc/theme_park/shared_player_transport_theed_hangar.iff', object.getPlanet().getName(), cell3.getObjectID(), float(0), float(7.979), float(0), float(1), float(1))
	
	core.mapService.addLocation(object.getPlanet(), 'Starport', object.getPosition().x, object.getPosition().z, 15, 0, 0)

	return