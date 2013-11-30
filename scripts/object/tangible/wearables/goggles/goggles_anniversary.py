import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/goggles/shared_goggles_anniverary.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
	
	return