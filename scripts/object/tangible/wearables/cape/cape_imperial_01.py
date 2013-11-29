import sys

def setup(core, object):
	object = core.objectService.creatObject('object/tangible/werables/cape/shared_cape_imperial_01.iff', object.getPlanet())
	object.setCustomName('Imperial Cape')
	object.setStringAttribute('condition', '100/100')
	object.setStringAttribute('volume', '1')
	object.setStringAttribute('cost', '[2500] Imperial Galactic Civil War token')
	object.setStringAttribute('faction_restriction', 'Imperial')
	
	inventory.add(object)
	
	return