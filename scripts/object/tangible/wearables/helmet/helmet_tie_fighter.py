import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/helmet/shared_helmet_tie_fighter.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
		
	return