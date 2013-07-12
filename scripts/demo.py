import sys

def CreateStartingCharacter(core, object):
	
	testObject = core.objectService.createObject('object/weapon/ranged/rifle/shared_rifle_t21.iff', object.getPlanet())
	testObject.setCustomName('This is a Jython Rifle')
	testObject.setStringAttribute('crafter', 'Light')

	inventory = object.getSlottedObject('inventory')
	inventory.add(testObject)
	
	testClothing = core.objectService.createObject('object/tangible/wearables/cape/shared_cape_rebel_01.iff', object.getPlanet())
	testClothing.setCustomName('Test Cape')
	testCloak = core.objectService.createObject('object/tangible/wearables/robe/shared_robe_jedi_dark_s05.iff', object.getPlanet())
	testCloak.setCustomName('Test Cloak')

	inventory.add(testClothing)
	inventory.add(testCloak)

	return
