import sys

def setup(core, actor, target, command):
	core.buffService.addBuffToCreature(target, 'of_vortex_bleed_1', actor)
	core.buffService.addBuffToCreature(target, 'of_vortex_root', actor)
	return
	
def run(core, actor, target, commandString):
	return