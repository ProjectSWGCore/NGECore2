import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/necklace/shared_necklace_trando_scale_of_honor_m.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
	
	return