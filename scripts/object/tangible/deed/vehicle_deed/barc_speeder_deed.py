import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/vehicleDeed')
	return

def use(core, actor, object):
	if object.getStfName() == ('barc_speeder'):
		datapad = actor.getSlottedObject('datapad')
		pcd = core.objectService.createObject('object/intangible/vehicle/shared_barc_speeder_pcd.iff', actor.getPlanet(), actor.getPosition().x, actor.getPosition().z,actor.getPosition().y)

		if datapad and pcd:
	
			datapad.add(pcd)
			core.objectService.destroyObject(object)
		
			vehicleId = actor.getAttachment('activeVehicleID')
		
		if vehicleId == None:
			vehicle = core.objectService.createObject('object/mobile/vehicle/shared_barc_speeder.iff', actor.getPlanet(), actor.getPosition().x, actor.getPosition().z,actor.getPosition().y)
			vehicle.setOwnerId(actor.getObjectID())
			core.simulationService.add(vehicle, vehicle.getPosition().x, vehicle.getPosition().z, True)
			actor.setAttachment('activeVehicleID', vehicle.getObjectID())
	elif object.getStfName() == ('item_deed_barc_rebel_06_01'):
		datapad = actor.getSlottedObject('datapad')
		pcd = core.objectService.createObject('object/intangible/vehicle/shared_barc_speeder_rebel_pcd.iff', actor.getPlanet(), actor.getPosition().x, actor.getPosition().z,actor.getPosition().y)

		if datapad and pcd:
	
			datapad.add(pcd)
			core.objectService.destroyObject(object)
		
			vehicleId = actor.getAttachment('activeVehicleID')
		
		if vehicleId == None:
			vehicle = core.objectService.createObject('object/mobile/vehicle/shared_barc_speeder_rebel.iff', actor.getPlanet(), actor.getPosition().x, actor.getPosition().z,actor.getPosition().y)
			vehicle.setOwnerId(actor.getObjectID())
			core.simulationService.add(vehicle, vehicle.getPosition().x, vehicle.getPosition().z, True)
			actor.setAttachment('activeVehicleID', vehicle.getObjectID())	
	return