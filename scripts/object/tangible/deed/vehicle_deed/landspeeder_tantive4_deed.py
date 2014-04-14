import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'vehicleDeed')
	return

def use(core, actor, object):
	datapad = actor.getSlottedObject('datapad')
	pcd = core.objectService.createObject('object/intangible/vehicle/shared_landspeeder_tantive4_pcd.iff', actor.getPlanet(), actor.getPosition().x, actor.getPosition().z,actor.getPosition().y)

	if datapad and pcd:
	
		datapad.add(pcd)
		core.objectService.destroyObject(object)
		
		vehicleId = actor.getAttachment('activeVehicleID')
		
		if vehicleId == None:
			vehicle = core.objectService.createObject('object/mobile/vehicle/shared_landspeeder_tantive4.iff', actor.getPlanet(), actor.getPosition().x, actor.getPosition().z,actor.getPosition().y)
			vehicle.setOwnerId(actor.getObjectID())
			core.simulationService.add(vehicle, vehicle.getPosition().x, vehicle.getPosition().z, True)
			actor.setAttachment('activeVehicleID', vehicle.getObjectID())
	
	return