import sys

def CombatMarksmanIthorianMale(core, object):
	belt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_belt_s20.iff', object.getPlanet())
	shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s11.iff', object.getPlanet())
	jacket = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_jacket_s07.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s04.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	object._add(belt)
	object._add(shirt)
	object._add(jacket)
	object._add(pants)