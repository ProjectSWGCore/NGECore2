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
		actor.sendSystemMessage('GM: Adjust Lot Count: Completed Successfully for ' + target.getFirstName(), 0)
	return