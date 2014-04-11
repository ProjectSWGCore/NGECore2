import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'harvester')
	object.setHarvester_type(1)
	object.setPowerCost(100); 
	object.setMaintenanceCost(120);
	return