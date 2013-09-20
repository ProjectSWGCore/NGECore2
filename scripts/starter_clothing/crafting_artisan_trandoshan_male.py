import sys

def CraftingArtisanTrandoshanMale(core, object):
	shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_shirt_s03.iff', object.getPlanet())
	vest = core.objectService.createObject('object/tangible/wearables/vest/shared_vest_s09.if', object.getPlanet())
	necklace = core.objectService.createObject('object/tangible/wearables/necklace/shared_necklace_primitive_03.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s13.ifff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	inventory.add(shirt)
	inventory.add(vest)
	inventory.add(necklace)
	inventory.add(pants)
	object._add(shirt)
	object._add(vest)
	object._add(necklace)
	object._add(pants)
