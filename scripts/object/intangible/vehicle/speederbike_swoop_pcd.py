import sys

def setup(core, object):
	#object.setAttachment('vehicleId', core.objectService.generateObjectID())
	object.setAttachment('radial_filename', 'item')
	return

def use(core, actor, object):
	if object:
		vehicleId = object.getObjectID()
		#vehicleId = object.getAttachment('vehicleId')
		
		if not vehicleId:
			return
		
		vehicle = core.objectService.getObject(vehicleId)
				
		swoop = core.objectService.createObject('object/mobile/vehicles/shared_speederbike_swoop.iff', vehicleId, actor.getPlanet(), actor.getPosition(), actor.getOrientation())
		core.simulationService.add(swoop, swoop.getPosition().x, swoop.getPosition().z, True)
	
	return