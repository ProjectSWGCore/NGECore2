import sys

def CombatMarksmanIthorianFemale(core, object):
	shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s09.iff', object.getPlanet())
	vest = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_vest_s01.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s01.iff', object.getPlanet())
	belt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_belt_s17.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	inventory.add(shirt)
	inventory.add(vest)
	inventory.add(pants)
	inventory.add(belt)
	object._addshirt)
	object._add(vest)
	object._add(pants)
	object._add(belt)