import sys

def OutdoorScoutHumanoidMale(core, object):
	vest = core.objectService.createObject('object/tangible/wearables/vest/shared_vest_hutt_gang_s01.iff', object.getPlanet())
	bracelet = core.objectService.createObject('object/tangible/wearables/bracelet/shared_primitive_bracelet.iff', object.getPlanet())
	pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s04.iff', object.getPlanet())
	belt = core.objectService.createObject('object/tangible/wearables/belt/shared_npe_belt_03.iff', object.getPlanet())
	boots = core.objectService.createObject('object/tangible/wearables/boots/shared_boots_s05.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	object._add(vest)
	object._add(bracelet)
	object._add(pants)
	object._add(belt)
	object._add(boots)
