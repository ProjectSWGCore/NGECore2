import sys

def CombatBrawlerHumanoidMale(core, object):
	shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_shirt_s42.iff', object.getPlanet())
	jacket = core.objectService.createObject('object/tangible/wearables/jacket/shared_jacket_s24.iff', object.getPlanet())
	gloves = core.objectService.createObject('object/tangible/wearables/gloves/shared_gloves_s06.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s04.iff', object.getPlanet())
	boots = core.objectService.createObject('object/tangible/wearables/boots/shared_boots_s22.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	inventory.add(shirt)
	inventory.add(jacket)
	inventory.add(gloves)
	inventory.add(pants)
	inventory.add(boots)
	object._add(shirt)
	object._add(jacket)
	object._add(gloves)
	object._add(pants)
	object._add(boots)
