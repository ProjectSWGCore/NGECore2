import sys

def setup(core, object):

	objSvc = core.objectService

	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_bank.iff', 0, 0.5, 4.2, 0, 1)
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_bank.iff', 0, 0.5, -4.2, 1, 0)
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_bank.iff', 4.2, 0.5, 0.707107, 0.707107, 1)
	objSvc.createChildObject(object, 'object/tangible/terminal/shared_terminal_bank.iff', -4.2, 0.5, 0, -0.707107, 0.707107)

	core.mapService.addLocation(object.getPlanet(), 'Bank Terminal', object.getPosition().x, object.getPosition().z, 2, 0, 0)
	return