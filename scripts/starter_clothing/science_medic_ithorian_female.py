import sys

def ScienceMedicIthorianFemale(core, object):
	shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s12.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s02.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	inventory.add(shirt)
	inventory.add(pants)
	object._add(shirt)
	object._add(pants)