from resources.objects.waypoint import WaypointObject
from engine.resources.common import CRC
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
                createWaypoint(core, actor, friendObj)
                return
        return
    return
   

def createWaypoint(core, actor, friendObj):
	ghost = actor.getSlottedObject('ghost')
	friendName = friendObj.getCustomName().split(' ')[0]
	wp = WaypointObject()
	
	for wp in ghost.getWaypoints():
		if wp.getName() == friendName and wp.getPlanet() == friendObj.getPlanet():
			core.commandService.callCommand(actor, 'serverdestroyobject', wp, '')
			break

	waypoint = core.objectService.createObject('object/waypoint/shared_waypoint.iff', friendObj.getPlanet(), friendObj.getWorldPosition().x, friendObj.getWorldPosition().z, friendObj.getWorldPosition().y)
	waypoint.setName(friendName)
	waypoint.setActive(True)
	waypoint.setColor(WaypointObject.YELLOW)
	waypoint.setName(friendName)
	waypoint.setPlanetCRC(CRC.StringtoCRC(friendObj.getPlanet().name))
	waypoint.setStringAttribute('', '')
	ghost.waypointAdd(waypoint)
	actor.sendSystemMessage('A waypoint has been created in your datapad at the location.', 0)
	return
