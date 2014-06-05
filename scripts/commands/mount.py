import sys

def setup():
    return

def run(core, actor, target, commandString):
	if not target:
		return
	
	if actor.getObjectID() != target.getOwnerId():
		return
	
	if core.mountService.isMounted(actor) is False:
		core.mountService.mount(actor, target)
	
	return
