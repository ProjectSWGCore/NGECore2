import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	if target:
		simService = core.simulationService
		simService.openContainer(actor, target)
	return
	