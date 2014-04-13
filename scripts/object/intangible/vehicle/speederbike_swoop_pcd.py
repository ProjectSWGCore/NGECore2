import sys

def setup(core, object):
<<<<<<< HEAD
	#object.setAttachment('vehicleId', core.objectService.generateObjectID())
	object.setAttachment('radial_filename', 'item')
	return

def use(core, actor, object):
	if object:
		vehicleId = object.getObjectID()
		#vehicleId = object.getAttachment('vehicleId')
=======
	object.setAttachment('radial_filename', 'vehicle_pcd')
	return

def use(core, actor, object):
	if object and actor.getAttachment('activeVehicleID') == None:
		swoop = core.objectService.createObject('object/mobile/vehicle/shared_speederbike_swoop.iff', 0, actor.getPlanet(), actor.getPosition(), actor.getOrientation())
>>>>>>> upstream/master
		
		swoop.setOwnerId(actor.getObjectID())
		actor.setAttachment('activeVehicleID', swoop.getObjectID())
		
<<<<<<< HEAD
		vehicle = core.objectService.getObject(vehicleId)
				
		swoop = core.objectService.createObject('object/mobile/vehicles/shared_speederbike_swoop.iff', vehicleId, actor.getPlanet(), actor.getPosition(), actor.getOrientation())
		core.simulationService.add(swoop, swoop.getPosition().x, swoop.getPosition().z, True)
	
=======
		core.simulationService.add(swoop, swoop.getPosition().x, swoop.getPosition().z, True)
>>>>>>> upstream/master
	return