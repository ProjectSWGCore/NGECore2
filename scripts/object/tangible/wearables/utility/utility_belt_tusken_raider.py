import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/utility/shared_utility_belt_tusken_raider.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
		
	return