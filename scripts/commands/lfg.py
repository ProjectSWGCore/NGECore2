from resources.common import PlayerFlags
import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    ghost = actor.getSlottedObject('ghost')
    print ('Command recieved!')
    ghost.toggleFlag(PlayerFlags.LFG)
    return