import sys

def setup(core, object):
	#object.setAttachment('vehicleId', core.objectService.generateObjectID())
	object.setAttachment('radial_filename', 'item')
	return

def use(core, actor, object):
	actor.sendSystemMessage('use', 0)
	if object:
		vehicleId = object.getObjectID()
		actor.sendSystemMessage('vehicleId %s' % vehicleId, 0)
		if not vehicleId:
			return
		
		vehicle = core.objectService.getObject(vehicleId)

		#if vehicle:
			#core.objectService.destroyObject(vehicle)
		#else:
		swoop = core.objectService.createObject('object/mobile/vehicle/shared_speederbike_swoop.iff', vehicleId, actor.getPlanet(), actor.getPosition(), actor.getOrientation())
		#core.simulationService.add(vehicle, vehicle.getPosition().x, vehicle.getPosition().z, True)
		core.simulationService.add(swoop, swoop.getPosition().x, swoop.getPosition().z, True)
	
	return