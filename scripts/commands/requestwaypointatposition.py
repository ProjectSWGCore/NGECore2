import sys

def setup():
    return
    
def run(core, actor, target, commandString):

    commandArgs = commandString.split("(^-,=+_)color_13548(,+-=_^)=1")
    newString = commandArgs[0]
	
    core.commandService.callCommand(actor, 'waypoint', None, newString)
    return