import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	
	if not target:
		return
	
	#actor.sendSystemMessage('LOOT SCRIPT COMMAND', 0)
	core.lootService.handleLootRequest(actor,target)
	return
	