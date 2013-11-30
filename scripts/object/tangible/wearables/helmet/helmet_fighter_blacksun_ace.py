import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/helmet/shared_helmet_fighter_blacksun_ace.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
		
	return