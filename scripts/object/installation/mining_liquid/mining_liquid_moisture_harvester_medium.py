import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'harvester')
	object.setHarvester_type(4)
	object.setPowerCost(60); 
	object.setMaintenanceCost(60);
	return