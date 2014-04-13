import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'vehicleDeed')
	return

def use(core, actor, object):
	datapad = actor.getSlottedObject('datapad')
<<<<<<< HEAD
	
	pcd = core.objectService.createObject('object/intangible/vehicle/shared_speederbike_swoop_pcd.iff', actor.getPlanet(), actor.getPosition().x, actor.getPosition().z,actor.getPosition().y)
	#actor.sendSystemMessage('actor.getObjectID() %s' % actor.getObjectID(), 0)
=======
	pcd = core.objectService.createObject('object/intangible/vehicle/shared_speederbike_swoop_pcd.iff', actor.getPlanet(), actor.getPosition().x, actor.getPosition().z,actor.getPosition().y)

>>>>>>> upstream/master
	if datapad and pcd:
	
		datapad.add(pcd)
		core.objectService.destroyObject(object)
<<<<<<< HEAD
		vehicle = core.objectService.createObject('object/mobile/vehicle/shared_speederbike_swoop.iff', actor.getPlanet(), actor.getPosition().x, actor.getPosition().z,actor.getPosition().y)
		vehicle.setOwnerId(actor.getObjectID())
		core.simulationService.add(vehicle, vehicle.getPosition().x, vehicle.getPosition().z, True)
=======
		
		vehicleId = actor.getAttachment('activeVehicleID')
		
		if vehicleId == None:
			vehicle = core.objectService.createObject('object/mobile/vehicle/shared_speederbike_swoop.iff', actor.getPlanet(), actor.getPosition().x, actor.getPosition().z,actor.getPosition().y)
			vehicle.setOwnerId(actor.getObjectID())
			core.simulationService.add(vehicle, vehicle.getPosition().x, vehicle.getPosition().z, True)
			actor.setAttachment('activeVehicleID', vehicle.getObjectID())
>>>>>>> upstream/master
	
	return