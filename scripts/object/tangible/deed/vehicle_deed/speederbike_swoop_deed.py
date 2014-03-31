import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'vehicleDeed')
	return

def use(core, actor, object):
	datapad = actor.getSlottedObject('datapad')
	
	pcd = core.objectService.createObject('object/intangible/vehicle/shared_speederbike_swoop_pcd.iff', actor.getPlanet(), actor.getPosition().x, actor.getPosition().z,actor.getPosition().y)
	
	if datapad and pcd:
		datapad.add(pcd)
		core.objectService.destroyObject(object)
		vehicle = core.objectService.createObject('object/mobile/vehicle/shared_speederbike_swoop.iff', actor.getPlanet(), actor.getPosition().x, actor.getPosition().z,actor.getPosition().y)
		vehicle.setOwnerId(actor.getObjectID())
		core.simulationService.add(vehicle, vehicle.getPosition().x, vehicle.getPosition().z, True)
		
	return