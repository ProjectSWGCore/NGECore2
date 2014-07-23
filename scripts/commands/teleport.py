import sys
from engine.resources.scene import Point3D

def setup():
    return
    
def run(core, actor, target, commandString):
	playerObject = actor.getSlottedObject('ghost')

	if not playerObject:
		return
	
	commandArgs = commandString.split(' ')
	arg4 = None
	if len(commandArgs) > 0:
		arg1 = commandArgs[0]
	if len(commandArgs) > 1:
		arg2 = commandArgs[1]
	if len(commandArgs) > 2:
		arg3 = commandArgs[2]
	if len(commandArgs) > 3:
		arg4 = commandArgs[3]
	
	if arg1 and arg2 and arg3 and arg4 is not None:
		try:
			actor.sendSystemMessage('gm: teleport: Command completed successfully.', 0)
			position = Point3D(float(arg1), float(arg2), float(arg3))
			core.simulationService.transferToPlanet(actor, core.terrainService.getPlanetByName(arg4), position, actor.getOrientation(), None)
		except:
			actor.sendSystemMessage('Invalid Syntax. Syntax is: /teleport <x> <z> [<y>] <planet>', 0)
			return
		return
	
	elif arg1 and arg2 and arg3:
		try:
			actor.sendSystemMessage('gm: teleport: Command completed successfully.', 0)
			position = Point3D(float(arg1), 0, float(arg2))
			core.simulationService.transferToPlanet(actor, core.terrainService.getPlanetByName(arg3), position, actor.getOrientation(), None)
		except:
			actor.sendSystemMessage('Invalid Syntax. Syntax is: /teleport <x> <z> [<y>] <planet>', 0)
		return
	
	else:
		actor.sendSystemMessage('Invalid Syntax. Syntax is: /teleport <x> <z> [<y>] <planet>', 0)
		return
	return