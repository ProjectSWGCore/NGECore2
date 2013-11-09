import sys

def setup(core, object):
	object.setPosture(0) # land = 0 : away = 2
	tp = core.travelService.getNearestTravelPoint(object)
	tp.setShuttle(object)
	tp.setShuttleAvailable(True)
	tp.setShuttleLanding(False)
	return