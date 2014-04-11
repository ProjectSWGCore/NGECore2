from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.clear()	
	radials.add(RadialOptions(0, 36, 0, 'Loot'))
	
	return
	
def handleSelection(core, owner, target, option):
	if option == 36 and target:
		core.lootService.handleLootRequest(owner,target)
	if option == 15 and target:
		core.objectService.destroyObject(target)
	return
	