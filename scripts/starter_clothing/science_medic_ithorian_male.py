import sys

def ScienceMedicIthorianMale(core, object):
	jacket = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_jacket_s01.iff', object.getPlanet())
	shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s04.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s18.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	inventory.add(jacket)
	inventory.add(shirt)
	inventory.add(pants)
	object._add(jacket)
	object._add(shirt)
	object._add(pants)
