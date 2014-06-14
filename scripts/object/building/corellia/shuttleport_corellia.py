import sys

def setup(core, object):

	objSvc = core.objectService
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_travel.iff', -13, 0.6, -10, -0.707107, 0.707107)
	objSvc.createChildObject(object, 'object/creature/npc/theme_park/shared_player_shuttle.iff', 0, 0.6, 0, 1, 0)
	objSvc.createChildObject(object, 'object/tangible/travel/ticket_collector/shared_ticket_collector.iff', -13, 0.6, 10, -0.707107, 0.707107)	
	
	
	core.mapService.addLocation(object.getPlanet(), 'Shuttleport', object.getPosition().x, object.getPosition().z, 14, 0, 0)
	
	return