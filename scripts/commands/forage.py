import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	actor.sendSystemMessage('@skl_use:sys_forage_start', 1)
	
	actor.setCurrentAnimation('forage')
	
	return
	