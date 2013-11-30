import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/armor/shared_appearance_invisible_bracer_r.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
	
	return