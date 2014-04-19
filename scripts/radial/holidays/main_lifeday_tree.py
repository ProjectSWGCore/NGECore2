from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.clear()
	player = owner.getSlottedObject('ghost')
	radials.add(RadialOptions(0, 7, 1, ''))
	if player and core.collectionService.getCollection(owner, 'col_lifeday_tree_tracking_2010') < 1:
		radials.add(RadialOptions(0, 21, 3, '@spam:tree_use'))
	if player and core.collectionService.getCollection(owner, 'lifeday_badge_08') < 1:
		radials.add(RadialOptions(0, 115, 3, '@spam:tree_badge'))
	return

def handleSelection(core, owner, target, option):
	actor = owner
	object = target
	
	if option == 115:
		core.collectionService.addCollection(owner, 'lifeday_badge_08')
		return
	
	if option != 21 or not object:
		return
	
	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
	
	if core.collectionService.isComplete(actor, 'col_lifeday_tree_tracking_2010'):
		return
	
	inventory = actor.getSlottedObject('inventory')
	
	if not inventory:
		return
	
	lifedaybox = core.objectService.createObject('object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff', actor.getPlanet(), 'item_lifeday_gift_self_01_01')
	lifedaybox.setAttachment('radial_filename', 'lifeday_gift')
	inventory.add(lifedaybox)
	
	lifedayboxfriend = core.objectService.createObject('object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff', actor.getPlanet(), 'item_lifeday_gift_other_01_01')
	lifedayboxfriend.setAttachment('ownerId', actor.getObjectID())
	inventory.add(lifedayboxfriend)
	
	core.collectionService.addCollection(owner, 'col_lifeday_tree_tracking_2010')
