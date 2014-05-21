import sys

def setup(core, actor, target, command):
	return
	
def run(core, actor, target, commandString):
	core.buffService.addBuffToCreature(actor, 'bh_shields', actor)
	core.buffService.addBuffToCreature(actor, 'bh_shields_charged', actor)
	core.buffService.addBuffToCreature(actor, 'bh_shields_handler', actor)
	return 