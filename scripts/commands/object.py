import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	
	if commandString.startswith('checklos') and target:
		print 'test'
		los = core.simulationService.checkLineOfSight(actor, target)
		if not los:
			actor.sendSystemMessage('-Cant see target-', 2)
		else:
			actor.sendSystemMessage('LOS ok', 2)


	return
	