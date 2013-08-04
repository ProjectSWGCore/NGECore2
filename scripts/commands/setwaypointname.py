import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    playerObject = actor.getSlottedObject('ghost')
    wp = playerObject.getWaypointFromList(target)
    if wp is not None:
        wp.setName(commandString)
        playerObject.waypointUpdate(wp)
    return