import sys

def CombatBrawlerOutdoorScoutTrandoshanFemale(core, object):
	shirt = core.objectService.createObject('object/tangible/wearables/shirt/shared_shirt_s24.iff', object.getPlanet())
	jacket = core.objectService.createObject('object/tangible/wearables/jacket/shared_jacket_s24.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s25.iff', object.getPlanet())
	necklace = core.objectService.createObject('object/tangible/wearables/necklace/shared_necklace_primitive_01.iff', object.getPlanet())
	object._add(shirt)
	object._add(jacket)
	object._add(pants)
	object._add(necklace)
