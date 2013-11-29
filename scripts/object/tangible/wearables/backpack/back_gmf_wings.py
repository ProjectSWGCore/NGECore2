import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/backpack/shared_back_gmf_wings.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setStringAttribute('volume', '1')
	inventory.add(object)
	
	return