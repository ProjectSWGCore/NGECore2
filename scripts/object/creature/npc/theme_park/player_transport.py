import sys

def setup(core, object):
	object.setPosture(0) # land = 0 : away = 2
	#object.setOptionsBitmask(1)
	tp = core.travelService.getNearestTravelPoint(object)
	tp.setShuttle(object)
	print ('assigned shuttle.')
	return