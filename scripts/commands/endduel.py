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
		return
		
	if not combatSvc.areInDuel(actor, target):
		return

	combatSvc.handleEndDuel(actor, target, True)

	return
	