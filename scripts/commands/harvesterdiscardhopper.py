import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	actor.sendSystemMessage('handleEmptyHarvester', 0)
	core.harvesterService.handleEmptyHarvester(actor, target, commandString)
	return

	