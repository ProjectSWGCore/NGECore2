import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    core.harvesterService.handleHarvesterActivateCommand(actor, target, commandString)
    return

	