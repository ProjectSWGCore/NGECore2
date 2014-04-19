import sys

def setup(core, object):
	objSvc = core.objectService
	
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_travel.iff', float(-26.94), float(0.75), float(-67.14), float(1), float(0), 3)
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_travel.iff', float(26.94), float(0.75), float(-67.14), float(1), float(0), 4)
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_travel.iff', float(-9.49), float(7.98), float(-45.24), float(0), float(1), 5)
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_travel.iff', float(9.49), float(7.98), float(-45.24), float(0), float(1), 5)
	objSvc.createChildObject(object, 'object/tangible/travel/ticket_collector/shared_ticket_collector.iff', float(-10), float(7.98), float(10), float(-0.71), float(0.71), 5)
	objSvc.createChildObject(object, 'object/creature/npc/theme_park/shared_player_transport_theed_hangar.iff', float(0), float(7.98), float(0), float(1), float(1), 5)
	
	core.mapService.addLocation(object.getPlanet(), 'Starport', object.getPosition().x, object.getPosition().z, 15, 0, 0)

	return