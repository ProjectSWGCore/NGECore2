import sys

def CraftingArtisanTrandoshanMale(core, object):
	shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_shirt_s03.iff', object.getPlanet())
	vest = core.objectService.createObject('object/tangible/wearables/vest/shared_vest_s09.iff', object.getPlanet())
	necklace = core.objectService.createObject('object/tangible/wearables/necklace/shared_necklace_primitive_03.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s13.iff', object.getPlanet())
	object._add(shirt)
	object._add(vest)
	object._add(necklace)
	object._add(pants)
