import sys

def setup(core, object):
	object = core.objectService.creatObject('object/tangible/werables/cape/shared_cape_rebel_01.iff', object.getPlanet())
	object.setCustomName('Rebel Alliance Cape')
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
	object.setStringAttribute('cost', '[2500] Rebel Alliance Galactic Civil War token')
	object.setStringAttribute('faction_restriction', 'Rebel Alliance')
	
	
	return