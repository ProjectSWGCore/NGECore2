import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	combatSvc = core.combatService

	if not target:
		return
		
	if not target.getClient():
		return
		
	if actor == target:
		actor.sendSystemMessage('You cannot challenge yourself.', 0)
		return
		
	if combatSvc.areInDuel(actor, target):
		return
		
	if actor.getDuelList().contains(target):
		actor.sendSystemMessage('You already challenged ' + target.getCustomName() + ' to a duel.', 0)
		return

	combatSvc.handleDuel(actor, target)
		
	return
	