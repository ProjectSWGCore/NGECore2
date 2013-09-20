import sys

def CraftingArtisanWookieeFemale(core, object):
	shirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_shirt_s02.iff', object.getPlanet())
	skirt = core.objectService.createObject('object/tangible/wearables/wookiee/shared_wke_skirt_s03.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	inventory.add(shirt)
	inventory.add(skirt)
	object._add(shirt)
	object._add(skirt)