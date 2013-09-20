import sys

def CombatMarksmanWookieeMale(core, object):
	bandolier = core.objectService.createObject('object/tangible/wearables/bandolier/shared_bandolier_s04.iff', object.getPlanet())
	shirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_shirt_s03.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	inventory.add(bandolier)
	inventory.add(shirt)
	object._add(bandolier)
	object._add(shirt)