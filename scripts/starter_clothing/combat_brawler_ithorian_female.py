import sys

def CombatBrawlerIthorianFemale(core, object):
	jacket = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_jacket_s06.iff', object.getPlanet())
	shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s01.iff', object.getPlanet())
	bandolier = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_bandolier_s04.iff', object.getPlanet())
	belt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_belt_s17.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s01.iff', object.getPlanet())
	gloves = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_gloves_s01.iff', object.getPlanet())

	inventory = object.getSlottedObject('inventory')
	inventory.add(jacket)
	inventory.add(shirt)
	inventory.add(bandolier)
	inventory.add(belt)
	inventory.add(pants)
	inventory.add(gloves)
	object._add(jacket)
	object._add(shirt)
	object._add(bandolier)
	object._add(belt)
	object._add(pants)
	object._add(gloves)