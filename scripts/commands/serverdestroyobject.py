from resources.common import Console
import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	if not target:
		return
	
	if target.getTemplate() == 'object/waypoint/shared_waypoint.iff':
		actor.getSlottedObject('ghost').waypointRemove(target)
		core.objectService.destroyObject(target)
		return
	
	parent = target.getContainer()
	
	if not target.getPermissions().canRemove(actor, target):
		return
	if parent and not parent.getPermissions().canRemove(actor, parent):
		Console.println("Doesn't have permission!")
		return
	
	#parent.remove(target)
	core.objectService.destroyObject(target)
	return
	