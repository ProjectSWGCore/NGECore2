import sys

def SocialEntertainerTrandoshanMale(core, object):
	vest = core.objectService.createObject('object/tangible/wearables/vest/shared_vest_s15.iff', object.getPlanet())
	skirt = core.objectService.createObject('object/tangible/wearables/skirt/shared_skirt_s05.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	object._add(vest)
	object._add(skirt)