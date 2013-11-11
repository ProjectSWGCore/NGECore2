import sys

def setup(core, actor, target, command):
	core.buffService.addBuffToCreature(target, 'co_del_ae_cc_2_2')
	return
	
def preRun(core, actor, target, command):
	return

def run(core, actor, target, commandString):
	return