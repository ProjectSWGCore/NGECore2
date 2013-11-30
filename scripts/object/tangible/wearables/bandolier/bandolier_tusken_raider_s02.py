import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/bandolier/shared_bandolier_tusken_raider_s02.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
	
	return