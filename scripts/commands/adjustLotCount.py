import sys

def run(core, actor, target, commandString):
	commandArgs = commandString.split(' ')
	arg1 = commandArgs[0]
		
	if target is None:
		target = actor
		
	if target:
		playerObject = target.getSlottedObject('ghost')
		playerObject.setLotsRemaining(playerObject.getLotsRemaining() + int(arg1))
		print(playerObject.getLotsRemaining())
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF adjustLotCount: Command completed successfully for: ' + target.getFirstName(), 0)
	return