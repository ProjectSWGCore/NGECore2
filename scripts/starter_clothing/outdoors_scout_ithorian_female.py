import sys

def OutdoorScoutIthorianFemale(core, object):
	shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s05.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s16.iff', object.getPlanet())
	jacket = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_jacket_s02.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	object._add(shirt)
	object._add(pants)
	object._add(jacket)