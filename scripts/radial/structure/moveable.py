from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.clear()
	radials.add(RadialOptions(0, 11, 1, ''))
	
	radials.add(RadialOptions(0, 55, 0, ''))
	radials.add(RadialOptions(3, 56, 1, ''))
	radials.add(RadialOptions(3, 57, 1, ''))
	radials.add(RadialOptions(3, 58, 1, ''))
	radials.add(RadialOptions(3, 59, 1, ''))
	
	radials.add(RadialOptions(0, 52, 0, ''))
	radials.add(RadialOptions(4, 53, 1, ''))
	radials.add(RadialOptions(4, 54, 1, ''))
	return
	
def handleSelection(core, owner, target, option):

	if option == 56:
		core.commandService.callCommand(owner, 'movefurniture', target, 'forward 10')
		
	return
	