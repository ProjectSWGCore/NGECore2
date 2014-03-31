import sys
from engine.resources.scene import Point3D

def setup():
    return
    
def run(core, actor, target, commandString):
	
	playerObject = actor.getSlottedObject('ghost')

	if not playerObject:
		return
	
	commandArgs = commandString.split(' ')
	if len(commandArgs) > 0:
		arg1 = commandArgs[0]
	if len(commandArgs) > 1:
		arg2 = commandArgs[1]
	if len(commandArgs) > 2:
		arg3 = commandArgs[2]
	if len(commandArgs) > 3:
		arg4 = commandArgs[3]
	
	if arg1 and arg2 and arg3 and arg4 target:
		position = Point3D(float(arg2), float(arg3), float(arg4))
		core.simulationService.transferToPlanet(target, core.terrainService.getPlanetByName(arg1), position, actor.getOrientation(), None)

	
	return
