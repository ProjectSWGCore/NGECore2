import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'vehicle')
	return

def use(core, actor, object):
	vehicle = actor.getContainer()
	
	if vehicle:
		if vehicle.getObjectID() == object.getObjectID():
			vehicle.remove(actor)
		else:
			vehicle.add(actor)
	
	return
import sys

def setup(core, object):
	return