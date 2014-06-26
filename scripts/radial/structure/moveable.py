from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 11, 0, ''))
	radials.add(RadialOptions(0, 55, 0, ''))
	radials.add(RadialOptions(0, 52, 0, ''))
	
	radials.add(RadialOptions(3, 56, 0, ''))
	radials.add(RadialOptions(3, 57, 0, ''))
	radials.add(RadialOptions(3, 58, 0, ''))
	radials.add(RadialOptions(3, 59, 0, ''))
		
	radials.add(RadialOptions(4, 53, 0, ''))
	radials.add(RadialOptions(4, 54, 0, ''))
	return 
	
def handleSelection(core, owner, target, option):

	if option == 56:
		core.commandService.callCommand(owner, 'movefurniture', target, 'forward 10')
		
	if option == 57:
		core.commandService.callCommand(owner, 'movefurniture', target, 'back 10')
		
	if option == 58:
		core.commandService.callCommand(owner, 'movefurniture', target, 'up 10')
		
	if option == 59:
		core.commandService.callCommand(owner, 'movefurniture', target, 'down 10')
		
	if option == 53:
		core.commandService.callCommand(owner, 'rotatefurniture', target, 'yaw -90')
		
	if option == 54:
		core.commandService.callCommand(owner, 'rotatefurniture', target, 'yaw 90')
		
	return
	