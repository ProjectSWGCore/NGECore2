import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	if actor:
		core.groupService.handleGroupDisband(actor)
	return
	