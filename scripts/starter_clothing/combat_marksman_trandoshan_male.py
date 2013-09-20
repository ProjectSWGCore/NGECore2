import sys

def CombatMarksmanTrandoshanMale(core, object):
	shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_npe_shirt.iff', object.getPlanet())
	jacket = core.objectService.createObject('object/tangible/wearables/jacket/shared_jacket_s16.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s21.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	inventory.add(shirt)
	inventory.add(jacket)
	inventory.add(pants)
	object._add(shirt)
	object._add(jacket)
	object._add(pants)