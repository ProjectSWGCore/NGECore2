import sys

def setup(core, object):
	object.setPosture(0) # land = 0 : away = 2
	object.setOptionsBitmask(256)
	core.travelService.addShuttleToPoint(object)
	return