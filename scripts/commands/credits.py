import sys



def run(core, actor, target, commandString):
	commandArgs = commandString.split(' ')
	arg1 = commandArgs[0]
	playerObject = actor.getSlottedObject('ghost')

	if actor:
		actor.setCashCredits(actor.getCashCredits() + int(arg1))
		actor.sendSystemMessage(actor.getFirstName() + ' has removed ' + arg1 + ' credits from Project SWG\'s bank.', 0)
		
	if actor and target:
		target.setCashCredits(target.getCashCredits() + int(arg1))
		target.sendSystemMessage('You have recieved ' + arg1 + ' credits from ' + actor.getFirstName(), 0)
		actor.sendSystemMessage('You have transferred ' + arg1 + ' credits to ' + target.getFirstName(), 0)

		
	return