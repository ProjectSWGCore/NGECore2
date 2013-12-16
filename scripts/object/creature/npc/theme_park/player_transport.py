import sys

def setup(core, object):
	object.setPosture(0) # land = 0 : away = 2
	object.setOptionsBitmask(256)

	tp = core.travelService.getNearestTravelPoint(object, 300)

	if tp and tp is not None:
		tp.setShuttle(object)
		tp.setShuttleAvailable(True)
		tp.setShuttleLanding(False)
		return
	return