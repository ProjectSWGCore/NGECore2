import sys

def setup(core, actor, target, command):
	return
	
def run(core, actor, target, commandString):
	core.buffService.addBuffToCreature(actor, 'bh_shields')
	core.buffService.addBuffToCreature(actor, 'bh_shields_charged')
	core.buffService.addBuffToCreature(actor, 'bh_shields_handler')
	return 