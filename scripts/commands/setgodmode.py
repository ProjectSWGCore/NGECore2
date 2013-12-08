import sys
from engine.resources.scene import Point3D

def setup():
    return
    
def run(core, actor, target, commandString):
	
	playerObject = actor.getSlottedObject('ghost')

	if not playerObject:
		return
	
	commandArgs = commandString.split(' ')
	command = commandArgs[0]
	arg1 = commandArgs[1]
	if commandArgs[2]:
		arg2 = commandArgs[2]
	if commandArgs[3]:
		arg3 = commandArgs[3]
	if commandArgs[4]:
		arg4 = commandArgs[4]
	
	if not command:
		return
	
	if command == 'giveExperience' and arg1:
		core.playerService.giveExperience(actor, int(arg1))
	
	if command == 'setSpeed' and arg1:
		actor.sendSystemMessage('Your speed was ' + str(actor.getSpeedMultiplierBase()) + '. Don\'t forget to set this back or it\'ll permanently imbalance your speed. Default without buffs or mods is 1.', 2)
		actor.setSpeedMultiplierBase(float(arg1))
		actor.sendSystemMessage('Your new speed is ' + str(actor.getSpeedMultiplierBase()) + '.', 2)
	
	if command == 'teleport' and arg2 and arg3 and arg4:
		position = Point3D(float(arg2), float(arg3), float(arg4))
		core.simulationService.transferToPlanet(actor, core.terrainService.getPlanetByName(arg1), position, actor.getOrientation(), None)
	
	return
