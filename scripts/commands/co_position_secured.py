import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	if actor.hasBuff('co_position_secured'):
		core.buffService.removeBuffFromCreature(actor, actor.getBuff('co_position_secured'))
	else:
		core.buffService.addBuffToCreature(actor, 'co_position_secured')
		
	return
	