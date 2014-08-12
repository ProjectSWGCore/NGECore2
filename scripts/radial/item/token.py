from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	if core.objectService.getItemsInContainerByStfName(owner, owner.getSlottedObject('inventory').getObjectId(), 'item_heroic_token_box_01_01').isEmpty() is False:
		radials.clear()
		radials.add(RadialOptions(0, 21, 1, 'Store One In Token Box'))
		radials.add(RadialOptions(1, 69, 2, 'Store Stack In Token Box'))
	return
	
def handleSelection(core, owner, target, option):
	if core.objectService.getItemsInContainerByStfName(owner, owner.getSlottedObject('inventory').getObjectId(), 'item_heroic_token_box_01_01').isEmpty() is False:
		tokenBox = core.objectService.getItemsInContainerByStfName(owner, owner.getSlottedObject('inventory').getObjectId(), 'item_heroic_token_box_01_01').get(0)	# Grabs the first one found - there should never be more than one box in the inventory
		
		if option == 21:
			tokenBox.setIntAttribute('@static_item_n:' + target.getStfName(), tokenBox.getIntAttribute('@static_item_n:' + target.getStfName()) + target.getStackCount())
			
			if target.getStackCount() - 1 is 0:
				core.objectService.destroyObject(target)
			else:
				target.setStackCount(target.getStackCount() - 1)
				
		if option == 69:
			tokenBox.setIntAttribute('@static_item_n:' + target.getStfName(), tokenBox.getIntAttribute('@static_item_n:' + target.getStfName()) + target.getStackCount())
			core.objectService.destroyObject(target)
			
		owner.sendSystemMessage('You add the ' + target.getTrueName() + ' to your ' + tokenBox.getTrueName() + '!', 0)	# Token name should be in a light green colour
	
	return
	