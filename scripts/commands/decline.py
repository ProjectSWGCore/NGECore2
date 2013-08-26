import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	if actor:
		core.groupService.handleGroupDecline(actor)
	return
	