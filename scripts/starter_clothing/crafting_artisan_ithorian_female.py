import sys

def CraftingArtisanIthorianFemale(core, object):
	dress = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_dress_s03.iff', object.getPlanet())
	hat = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_hat_s02.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	inventory.add(dress)
	inventory.add(hat)
	object._add(dress)
	object._add(hat)
