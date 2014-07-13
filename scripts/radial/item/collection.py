from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 21, 1, ''))
	radials.add(RadialOptions(0, 7, 1, ''))
	radials.add(RadialOptions(0, 21, 3, '@collection:consume_item'))
	return

def handleSelection(core, actor, target, option):
	if option == 21 and target:
		if target.getAttachment('CollectionItemName'):
			print (target.getAttachment('CollectionItemName'))
			if core.collectionService.isComplete(actor, target.getAttachment('CollectionItemName')) == False:
				core.collectionService.addCollection(actor, target.getAttachment('CollectionItemName'))
				core.objectService.useObject(actor, target)
				core.objectService.destroyObject(target)
	return
