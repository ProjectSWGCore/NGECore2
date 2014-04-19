import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    core.harvesterService.handleHarvesterSelectResourceCommand(actor, target, commandString)
    return

	