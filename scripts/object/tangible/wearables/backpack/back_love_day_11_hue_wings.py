import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/backpack/shared_back_love_day_11_hue_wings.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
	
	return