import sys

def setup():
    return

def run(core, actor, target, commandString):
	if actor.canMount(target) is False: return

	if not actor.isMounted():
		actor.setMounted(1)
		target.initMount(actor)	
	else:
		target.unmount(actor)
    
	return

