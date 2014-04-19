import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'vehicle_pcd')
	return

def use(core, actor, object):
	if object and actor.getAttachment('activeVehicleID') == None:
		tantive4 = core.objectService.createObject('object/mobile/vehicle/shared_landspeeder_tantive4.iff', 0, actor.getPlanet(), actor.getPosition(), actor.getOrientation())
		
		tantive4.setOwnerId(actor.getObjectID())
		actor.setAttachment('activeVehicleID', tantive4.getObjectID())
		
		core.simulationService.add(tantive4, tantive4.getPosition().x, tantive4.getPosition().z, True)
	return