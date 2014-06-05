from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 49, 0, 'Split'))
	radials.add(RadialOptions(0, 7, 0, 'Examine'))
	radials.add(RadialOptions(0, 15, 0, 'Destroy'))
	return
	
def handleSelection(core, owner, target, option):
	#if option == 21 and target:
		#core.objectService.useObject(owner, target)
	if option == 15 and target:
		if target == player.getRecentContainer():
			player.setRecentContainer(None)
			
		core.objectService.destroyObject(target)
	return