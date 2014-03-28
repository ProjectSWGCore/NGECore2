from resources.datatables import PlayerFlags
import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    ghost = actor.getSlottedObject('ghost')
    ghost.toggleFlag(PlayerFlags.LFG)
    return