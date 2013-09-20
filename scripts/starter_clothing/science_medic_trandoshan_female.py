import sys

def ScienceMedicTrandoshanFemale(core, object):
	jacket = core.objectService.createObject('object/tangible/wearables/jacket/shared_jacket_s26.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s08.iff', object.getPlanet())
	necklace = core.objectService.createObject('object/tangible/wearables/necklace/shared_necklace_s01.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	inventory.add(jacket)
	inventory.add(pants)
	inventory.add(necklace)
	object._add(jacket)
	object._add(pants)
	object._add(necklace)