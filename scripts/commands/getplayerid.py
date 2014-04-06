import sys
 
def setup():
    return
   
def run(core, actor, target, commandString):
	commandArgs = commandString.split(" ")
	target = core.chatService.getObjectByFirstName(commandArgs[0])
	
	if len(commandArgs) > 1:
		if commandArgs[1].startswith("id"):
			actor.sendSystemMessage('Player Object ID: ' + target.getObjectId())
			return
	
		elif commandArgs[1].startswith("pid"):
			actor.sendSystemMessage('Account ID: ' + str(core.characterService.getAccountId(target.getObjectId())), 0)
			return
	else:
		actor.sendSystemMessage('Player Object ID: ' + str(target.getObjectId()) + ' Account ID: ' + str(core.characterService.getAccountId(target.getObjectId())), 0)
		return
	return