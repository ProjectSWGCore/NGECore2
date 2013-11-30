import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/armor/shared_appearance_invisible_bicep_l.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
	
	return