import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    ghost = actor.getSlottedObject('ghost')
    friend = commandString.split(' ')[0]
    
    if ghost.getFriendList().contains(friend):
        friendObj = core.chatService.getObjectByFirstName(friend)
        if friendObj is not None:
            if friendObj.getSlottedObject('ghost').getFriendList().contains(actor.getCustomName().lower().split(' ')[0]):
                fPos = friendObj.getPosition()
                core.commandService.callCommand(actor, 'waypoint', None, friendObj.getPlanet().name + ' ' + str(fPos.x) + ' ' + str(fPos.z) + ' ' + str(fPos.y) + ' blue ' + friendObj.getCustomName())
                return
        return
    return