import sys

def CombatBrawlerTrandoshanMale(core, object):
	shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_shirt_s42.iff', object.getPlanet())
	jacket = core.objectService.createObject('object/tangible/wearables/jacket/shared_jacket_s24.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s04.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	object._add(shirt)
	object._add(jacket)
	object._add(pants)
