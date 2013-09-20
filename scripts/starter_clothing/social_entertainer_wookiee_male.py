import sys

def SocialEntertainerWookieeMale(core, object):
	gloves = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_gloves_s04.iff', object.getPlanet())
	hood = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_hood_s03.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	inventory.add(gloves)
	inventory.add(hood)
	object._add(gloves)
	object._add(hood)