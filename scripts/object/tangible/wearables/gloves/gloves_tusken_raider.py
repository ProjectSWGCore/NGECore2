import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/gloves/shared_gloves_tusken_raider.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
	
	return