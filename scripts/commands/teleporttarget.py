import sys
from engine.resources.scene import Point3D

def setup():
    return
    
def run(core, actor, target, commandString):

	playerObject = actor.getSlottedObject('ghost')

	if not playerObject:
		return
	
	if target and target.getSlottedObject('ghost') is not None:
		actor.sendSystemMessage('gm: teleporttarget: Command completed successfully.', 0)
		core.simulationService.teleport(target, actor.getPosition(), actor.getOrientation(), 0)
		return
	
	else:
		actor.sendSystemMessage('Invalid Syntax. Syntax is: /teleporttarget <HoverTarget>', 0)
		return
	return
