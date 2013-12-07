import sys

def setup():
    return
    
def run(core, actor, target, commandString):

    commandArgs = commandString.split("(^-,=+_)color_13548(,+-=_^)=1")

    newString = commandArgs[0]
    print (newString)
    cmdService = core.commandService
    cmdService.callCommand(actor, 'waypoint', None, newString) # No need to re-create a script
    return