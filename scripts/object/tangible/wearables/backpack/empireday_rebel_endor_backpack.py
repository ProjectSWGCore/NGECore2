import sys

def setup(core, object):

	object = core.objectService.createObject('object/tangible/wearables/backpack/shared_empireday_rebel_endor_backpack.iff', object.getPlanet())
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
	object.setStringAttribute('cost', '[15] Remembrance Day Token')
	object.setStringAttribute('faction_restriction', 'Rebel Alliance')
	
	return