import sys

def setup():
	return

def run(core, actor, target, commandString):
	if not target:
		return
	
	if actor.getObjectID() != target.getOwnerId():
		return
	
	
	if core.mountService.isMounted(actor) is True:
		core.mountService.dismount(actor, target)
	
	return

