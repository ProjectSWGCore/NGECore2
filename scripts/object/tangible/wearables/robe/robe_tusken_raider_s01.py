import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/robe/shared_robe_tusken_raider_s01.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
	
	return