import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'item')
	return

def use(core, actor, object):
	pcd = actor.getSlottedObject('datapad')
	
	pcd = createObject('object/intangible/vehicle/shared_speederbike_swoop_pcd.iff', actor.getPlanet(), actor.getPosition)
	
	if datapad and pcd:
		datapad.add(pcb)
		core.objectService.destroyObject(object)
		vehicle = createObject('object/mobile/vehicle/shared_speederbike_swoop.iff', actor.getPlanet(), actor.getPosition())
		vehicle.setOwnerId(actor.getObjectID())
		core.simulationService.add(vehicle, vehicle.getPosition().x, vehicle.getPosition().z, True)
	
	return