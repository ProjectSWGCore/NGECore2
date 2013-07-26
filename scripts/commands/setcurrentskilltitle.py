import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    playerObject = actor.getSlottedObject('ghost')
    playerObject.setTitle(str(commandString))
    return