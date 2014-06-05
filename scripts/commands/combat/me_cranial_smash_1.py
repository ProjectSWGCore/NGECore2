import sys

def setup(core, actor, target, command):
	core.buffService.addBuffToCreature(target, 'me_cranial_smash_1', actor)
	return
	
def preRun(core, actor, target, command):
	return

def run(core, actor, target, commandString):
	return