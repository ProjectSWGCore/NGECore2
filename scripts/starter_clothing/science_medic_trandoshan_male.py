import sys

def ScienceMedicTrandoshanMale(core, object):
	shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_npe_shirt.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s01.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	object._add(shirt)
	object._add(pants)