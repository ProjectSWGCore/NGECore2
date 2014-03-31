from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 7, 1, ''))
	radials.add(RadialOptions(0, 15, 1, ''))
	radials.add(RadialOptions(0, 61, 1, ''))
	return
	
def handleSelection(core, owner, target, option):
	if option == 7 and target:
		owner.sendSystemMessage('7', 0)
	if option == 61 and target:
		owner.sendSystemMessage('61', 0)
		core.objectService.useObject(owner, target)
	if option == 15 and target:
		core.objectService.destroyObject(target)
	return
	