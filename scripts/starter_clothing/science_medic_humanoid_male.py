import sys

def ScienceMedicHumanoidMale(core, object):
	shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_npe_shirt.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s01.iff', object.getPlanet())
	shoes = core.objectService.createObject('object/tangible/wearables/shoes/shared_shoes_s02.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	inventory.add(shirt)
	inventory.add(pants)
	inventory.add(shoes)
	object._add(shirt)
	object._add(pants)
	object._add(shoes)