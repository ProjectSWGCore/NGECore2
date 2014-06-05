import sys

def setup(core, object):
	objSvc = core.objectService
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_travel.iff', float(11.5), float(0.6), float(51.3), float(0.195405), float(-0.980722), 4)	
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_travel.iff', float(3.1), float(0.6), float(49), float(0), float(1), 4)	
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_travel.iff', float(-2.7), float(0.6), float(49), float(0), float(1), 4)	
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_travel.iff', float(-13.2), float(0.6), float(51.3), float(-0.195405), float(-0.980722), 4)	
	objSvc.createChildObject(object, 'object/creature/npc/theme_park/shared_player_transport.iff', 0, 0, 0, 1, 0)
	objSvc.createChildObject(object, 'object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 10, 0, -10, 0.707107, 0.707107)
	core.mapService.addLocation(object.getPlanet(), 'Starport', object.getPosition().x, object.getPosition().z, 15, 0, 0)
	return
	