import sys

def SocialEntertainerTwilekFemale(core, object):
	bracelet_l = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s05_l.iff', object.getPlanet())
	bracelet_r = core.objectService.createObject('object/tangible/wearables/bracelet/shared_bracelet_s05_r.iff', object.getPlanet())
	necklace = core.objectService.createObject('object/tangible/wearables/necklace/shared_necklace_s12.iff', object.getPlanet())
	shoes = core.objectService.createObject('object/tangible/wearables/shoes/shared_shoes_s07.iff', object.getPlanet())
	skirt = core.objectService.createObject('object/tangible/wearables/skirt/shared_npe_entertainer_skirt.iff', object.getPlanet())
	bustier = core.objectService.createObject('object/tangible/wearables/bustier/shared_bustier_s01.iff', object.getPlanet())
	hat = core.objectService.createObject('object/tangible/wearables/hat/shared_hat_twilek_s03.iff', object.getPlanet())
	inventory = object.getSlottedObject('inventory')
	object._add(bracelet_l)
	object._add(bracelet_r)
	object._add(necklace)
	object._add(shoes)
	object._add(skirt)
	object._add(bustier)
	object._add(hat)