import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    playerObject = actor.getSlottedObject('ghost')
    if target is None:
        return
    waypointTarget = core.objectService.getObject(target.getObjectID())
    wp = playerObject.getWaypointFromList(target)
    if wp.isActive() == True:
        wp.setActive(False)
        playerObject.waypointUpdate(wp)
        return
    else:
        wp.setActive(True)
        playerObject.waypointUpdate(wp)
    return