import sys

def SocialEntertainerIthorianMale(core, object):
	pants = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_pants_s10.iff', object.getPlanet())
	shirt = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_shirt_s08.iff', object.getPlanet())
	dress = core.objectService.createObject('object/tangible/wearables/ithorian/shared_ith_dress_short_s01.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	object._add(pants)
	object._add(shirt)
	object._add(dress)