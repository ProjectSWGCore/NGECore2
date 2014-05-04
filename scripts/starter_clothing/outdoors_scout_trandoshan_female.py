import sys

def OutdoorScoutTrandoshanFemale(core, object):
	vest = core.objectService.createObject('object/tangible/wearables/vest/shared_vest_s04.iff', object.getPlanet())
	bustier = core.objectService.createObject('object/tangible/wearables/bustier/shared_bustier_s02.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s13.iff', object.getPlanet())
	bracelet_l = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s05_l.iff', object.getPlanet())
	bracelet_r = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s05_r.iff', object.getPlanet())
	belt = core.objectService.createObject('object/tangible/wearables/belt/shared_npe_belt_02.iff', object.getPlanet())
	necklace = core.objectService.createObject('object/tangible/wearables/necklace/shared_necklace_primitive_03.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	object._add(vest)
	object._add(bustier)
	object._add(pants)
	object._add(bracelet_l)
	object._add(bracelet_r)
	object._add(belt)
	object._add(necklace)
