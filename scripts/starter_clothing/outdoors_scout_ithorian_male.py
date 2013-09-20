import sys

def OutdoorScoutIthorianMale(core, object):
	shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s13.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s12.iff', object.getPlanet())
	jacket = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_jacket_s03.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	inventory.add(shirt)
	inventory.add(pants)
	inventory.add(jacket)
	object._add(shirt)
	object._add(pants)
	object._add(jacket)