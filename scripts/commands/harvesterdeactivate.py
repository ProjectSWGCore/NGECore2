import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    core.harvesterService.handleHarvesterDeactivateCommand(actor, target, commandString)
    return

	