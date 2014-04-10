import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'generator')
	object.setHarvester_type(5)
	object.setMaintenanceCost(30)
	object.setGenerator(1)
	return