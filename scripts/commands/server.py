import sys
from engine.resources.scene import Point3D
from resources.datatables import GalaxyStatus

def setup():
    return
    
def run(core, actor, target, commandString):
	
	playerObject = actor.getSlottedObject('ghost')

	if not playerObject:
		return
	
	commandArgs = commandString.split(' ')
	command = commandArgs[0]
	if len(commandArgs) > 1:
		arg1 = commandArgs[1]
	if len(commandArgs) > 2:
		arg2 = commandArgs[2]
	if len(commandArgs) > 3:
		arg3 = commandArgs[3]
	if len(commandArgs) > 4:
		arg4 = commandArgs[4]
	
	if not command:
		return
	
	if command == 'lockServer':
		core.setGalaxyStatus(GalaxyStatus.Locked)
	if command == 'unlockServer':
		core.setGalaxyStatus(GalaxyStatus.Online)
	if command == 'info':
		actor.sendSystemMessage(str(core.getActiveZoneClients()) + ' online characters.', 0)
	if command == 'shutdown':
		core.initiateShutdown()
	if command == 'getheight':
		actor.sendSystemMessage(str(core.terrainService.getHeight(actor.getPlanetId(), actor.getWorldPosition().x, actor.getWorldPosition().z)), 0)

		
	return
