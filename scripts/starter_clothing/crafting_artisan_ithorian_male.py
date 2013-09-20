import sys

def CraftingArtisanIthorianMale(core, object):
	shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s10.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s06.iff', object.getPlanet())
	vest = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_vest_s02.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	inventory.add(shirt)
	inventory.add(pants)
	inventory.add(vest)
	object._add(shirt)
	object._add(pants)
	object._add(vest)