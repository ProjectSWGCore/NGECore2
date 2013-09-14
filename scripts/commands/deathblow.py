import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	combatSvc = core.combatService

	if not target:
		return
		
	if actor.getPosture() == 13 or actor.getPosture() == 14:
		return
		
	if not target.getPosture() == 13:
		return
		
	if not target.isAttackableBy(actor):
		return
		
	if not target.inRange(actor.getPosition(), 5):
		return
	
	combatSvc.deathblowPlayer(actor, target)

	return
	