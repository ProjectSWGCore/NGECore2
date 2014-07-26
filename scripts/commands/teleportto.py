import sys
from engine.resources.scene import Point3D

def setup():
    return
    
def run(core, actor, target, commandString):

	playerObject = actor.getSlottedObject('ghost')

	if not playerObject:
		return
	
	if target is None:
		target = core.objectService.getObjectByFirstName(commandString)
	
	if target and target.getSlottedObject('ghost') is not None:
		actor.sendSystemMessage('gm: teleportto: Command completed successfully.', 0)
		if target.getPlanetId() == actor.getPlanetId():
			core.simulationService.teleport(actor, target.getPosition(), target.getOrientation(), target.getParentId())
		else:
			if target.getParentId() == 0:
				core.simulationService.transferToPlanet(actor, core.terrainService.getPlanetById(target.getPlanetId()), target.getPosition(), target.getOrientation(), None)
			else:
				core.simulationService.transferToPlanet(actor, core.terrainService.getPlanetById(target.getPlanetId()), target.getPosition(), target.getOrientation(), core.objectService.getObject(target.getParentId()))
		return
	else:
		actor.sendSystemMessage('Invalid Syntax. Syntax is: /teleportto <playerName>', 0)
		return
	return
