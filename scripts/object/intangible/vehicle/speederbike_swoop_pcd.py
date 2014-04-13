import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'vehicle_pcd')
	return

def use(core, actor, object):
	if object and actor.getAttachment('activeVehicleID') == None:
		swoop = core.objectService.createObject('object/mobile/vehicle/shared_speederbike_swoop.iff', 0, actor.getPlanet(), actor.getPosition(), actor.getOrientation())
		
		swoop.setOwnerId(actor.getObjectID())
		actor.setAttachment('activeVehicleID', swoop.getObjectID())
		
		core.simulationService.add(swoop, swoop.getPosition().x, swoop.getPosition().z, True)
	return