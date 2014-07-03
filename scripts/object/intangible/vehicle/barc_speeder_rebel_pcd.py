import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'datapad/vehicle_pcd')
	object.setIntAttribute('no_trade', 1)
	object.setStringAttribute('faction_restriction', 'Rebel')
	return