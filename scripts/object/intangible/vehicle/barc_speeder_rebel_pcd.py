import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'datapad/vehicle_pcd')
	object.setIntAttribute('no_trade', 1)
	object.setStringAttribute('faction_restriction', 'Rebel')
	return

def use(core, actor, object):
	if object and actor.getAttachment('activeVehicleID') == None:
		barc = core.objectService.createObject('object/mobile/vehicle/shared_barc_speeder_rebel.iff', 0, actor.getPlanet(), actor.getPosition(), actor.getOrientation())
		
		barc.setOwnerId(actor.getObjectID())
		actor.setAttachment('activeVehicleID', barc.getObjectID())
		
		core.simulationService.add(barc, barc.getPosition().x, barc.getPosition().z, True)
	return