import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/armor/nightsister/shared_armor_nightsister_bicep_r_s01.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
	
	return