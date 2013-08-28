import sys

def setup(core, actor, target, command):
	return
	
def preRun(core, actor, target, command):
	return

def run(core, actor, target, commandString):
	if actor and target:
		core.buffService.addBuffToCreature(target, 'me_rheumatic_calamity_1')
	return
