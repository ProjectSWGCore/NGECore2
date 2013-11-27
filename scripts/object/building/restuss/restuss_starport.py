import sys

def setup(core, object):

	stcSvc = core.staticService
	objSvc = core.objectService
	objSvc.createChildObject(object, 'object/creature/npc/theme_park/shared_player_transport.iff', 0, 7, 0, 0.707107, 0.707107)
	objSvc.createChildObject(object, 'object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 1, 0, -10, 0, 1)	

	cell = object.getCellByCellNumber(1)
	
	if cell:
		stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', object.getPlanet().getName(), cell.getObjectID(), float(-3.12), float(0.14659503), float(-17.57), float(0.707107), float(-0.707107)) 


	return