import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	buffObj = actor.getBuffList().get(int(commandString))
	
	if buffObj is None:
		return
	
	if buffObj.isRemovableByPlayer() and buffObj.isDebuff() is False:
		core.buffService.removeBuffFromCreature(actor, buffObj)
	return
