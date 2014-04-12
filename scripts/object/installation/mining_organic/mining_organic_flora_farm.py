import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'harvester')
	object.setHarvester_type(2)
	object.setPowerCost(25); 
	object.setMaintenanceCost(16);
	return