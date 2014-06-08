from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 21, 1, ''))
	radials.add(RadialOptions(0, 7, 1, ''))
	radials.add(RadialOptions(0, 21, 3, '@collection:consume_item'))
	return
	
def handleSelection(core, owner, target, option):
	if option == 21 and target:
		core.collectionService.addCollection(owner, target.getStfName().rsplit('_n', 1)[0])
		core.objectService.useObject(owner, target)
	return
	