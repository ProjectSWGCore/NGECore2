from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	#owner.sendSystemMessage('Correct Loot Radial created', 0)
	radials.clear()	
	radials.add(RadialOptions(0, 36, 0, 'Loot'))
	if target.getAttachment('AI') and core.resourceService and core.resourceService.canHarvest(owner, target):
		radials.add(RadialOptions(0, 163, 3, '@sui:harvest_corpse'))
		radials.add(RadialOptions(2, 164, 3, '@sui:harvest_meat'))
		radials.add(RadialOptions(2, 165, 3, '@sui:harvest_hide'))
		radials.add(RadialOptions(2, 166, 3, '@sui:harvest_bone'))
		
		
	return
	
def handleSelection(core, owner, target, option):
	
	if option == 36 and target:
		#core.lootService.handleLootRequest(owner,target)
		core.lootService.handleLootRequest(owner,target)
		#lootedObjectInventory = target.getSlottedObject("inventory")
		#core.simulationService.openContainer(owner, lootedObjectInventory)
		#core.lootService.handleCreditDrop(owner, lootedObjectInventory)
	if option == 15 and target:
		core.objectService.destroyObject(target)
	if option == 164 and target.getAttachment('AI') and core.resourceService.canHarvest(owner, target):
		core.resourceService.doHarvest(owner, target, 'meat')
	if option == 165 and target.getAttachment('AI') and core.resourceService.canHarvest(owner, target):
		core.resourceService.doHarvest(owner, target, 'hide')
	if option == 166 and target.getAttachment('AI') and core.resourceService.canHarvest(owner, target):
		core.resourceService.doHarvest(owner, target, 'bone')	
	return
	