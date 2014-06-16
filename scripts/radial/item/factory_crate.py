from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.clear()
	radials.add(RadialOptions(0, 44, 0, 'Get Item From Crate'))
	radials.add(RadialOptions(0, 49, 0, 'Split'))
	radials.add(RadialOptions(0, 7, 0, 'Examine'))
	radials.add(RadialOptions(0, 15, 0, 'Destroy'))
	return
	
def handleSelection(core, owner, target, option):
	if option == 44 and target:
		target.getObjectOutOfCrate(owner)		
	if option == 15 and target:	
		core.objectService.destroyObject(target)
	#if option == 49 and target:	
		#target.splitFactoryCrate(owner, target, option)
	return