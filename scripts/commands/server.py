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
		actor.sendSystemMessage('GM: Command completed successfully, Server is now in Locked Status', 0)
	if command == 'unlockServer':
		core.setGalaxyStatus(GalaxyStatus.Online)
		actor.sendSystemMessage('GM: Command completed successfully, Server is now in Online Status', 0)
	#if command == 'restart': //This command currently causes an error at NGEcore.java like 593 whole attempting to close databaseConnection2
		#core.restart()
		#actor.sendSystemMessage('GM: Command completed successfully, Server restart initiated', 0)
	if command == 'shutdown':
		core.initiateShutdown()
		actor.sendSystemMessage('GM: Command completed successfully, Server shutdown initiated', 0)
	if command == 'stop':
		core.initiateStop()
		actor.sendSystemMessage('GM: Command completed successfully, Emergency server shutdown initiated', 0)
		
	return
