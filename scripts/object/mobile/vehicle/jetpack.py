import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'creature/vehicle')
	object.setCustomizationVariable('/private/index_hover_height', 40)
	object.setAttachment('isAutoMount', 'true')
	object.setAttachment('isAutoStore', 'true')
	return