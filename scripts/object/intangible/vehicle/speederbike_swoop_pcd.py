import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'item')
	return

def use(core, actor, object):
	if object:				
		swoop = core.objectService.createObject('object/mobile/vehicle/shared_speederbike_swoop.iff', 0, actor.getPlanet(), actor.getPosition(), actor.getOrientation())
		core.simulationService.add(swoop, swoop.getPosition().x, swoop.getPosition().z, True)
	
	return