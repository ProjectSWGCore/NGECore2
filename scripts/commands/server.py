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
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Server: Invalid Syntax. Syntax is: /server <lockServer | unlockServer | shutdown | stop>.', 0)
		return
	
	if command == 'lockServer':
		core.setGalaxyStatus(GalaxyStatus.Locked)
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Server lockServer: Command completed successfully. Server is now in Locked Status.', 0)
	if command == 'unlockServer':
		core.setGalaxyStatus(GalaxyStatus.Online)
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Server unlockServer: Command completed successfully. Server is now in Online Status.', 0)
	#if command == 'restart': //This command currently causes an error at NGEcore.java like 593 whole attempting to close databaseConnection2
		#core.restart()
		#actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Server restart: Command completed successfully. Server restart initiated.', 0)
	if command == 'shutdown':
		core.initiateShutdown()
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Server shutdown: Command completed successfully. Server shutdown initiated.', 0)
	if command == 'stop':
		core.initiateStop()
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Server stop: Command completed successfully. Emergency server shutdown initiated.', 0)
		
	return
