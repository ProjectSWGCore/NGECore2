import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/jacket/shared_jacket_lifeday_09_01.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
	
	return