import sys

def CombatBrawlerIthorianMale(core, object):
	jacket = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_jacket_s10.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s02.iff', object.getPlanet())
	bandolier = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_bandolier_s04.iff', object.getPlanet())
	object._add(jacket)
	object._add(pants)
	object._add(bandolier)