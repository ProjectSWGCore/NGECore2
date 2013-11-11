import sys

def setup(core, actor, target, command):
	core.buffService.addBuffToCreature(target, 'co_killing_spree')
	return
	
def preRun(core, actor, target, command):
	return

def run(core, actor, target, commandString):
	return