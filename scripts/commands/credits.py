import sys



def run(core, actor, target, commandString):
	commandArgs = commandString.split(' ')
	arg1 = commandArgs[0]
	playerObject = actor.getSlottedObject('ghost')

	if actor:
		actor.setCashCredits(actor.getCashCredits() + int(arg1))
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Credits: Command completed successfully. You have given yourself ' + arg1 + ' credits.', 0)
		
	if actor and target:
		target.setCashCredits(target.getCashCredits() + int(arg1))
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Credits: Command completed successfully. You have transferred ' + arg1 + ' credits to ' + target.getFirstName() + '.', 0)

	return