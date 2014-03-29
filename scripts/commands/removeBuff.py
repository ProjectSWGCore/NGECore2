import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	buffObj = actor.getBuffByCRC(int(commandString))
	
	if buffObj is None:
		return

	core.buffService.removeBuffFromCreature(actor, buffObj)
	return