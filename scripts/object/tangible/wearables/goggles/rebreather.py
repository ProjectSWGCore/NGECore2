import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/goggles/shared_rebreather.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setStringAttribute('volume', '1')
	inventory.add(object)
	
	return