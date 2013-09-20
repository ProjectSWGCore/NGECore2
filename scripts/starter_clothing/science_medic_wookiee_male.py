import sys

def ScienceMedicWookieeMale(core, object):
	hood = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_hood_s02.iff', object.getPlanet())
	bandolier = core.objectService.createObject('object/tangible/wearables/bandolier/shared_bandolier_s09.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	inventory.add(hood)
	inventory.add(bandolier)
	object._add(hood)
	object._add(bandolier)