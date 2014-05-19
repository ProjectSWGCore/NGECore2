import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	if actor and target:
		core.groupService.handleGroupKick(actor, target)
	return
	