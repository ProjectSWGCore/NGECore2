import sys

def setup(core, actor, target, command):
	core.buffService.addBuffToCreature(target, 'en_void_dance_buff_2')
	return
	
def preRun(core, actor, target, command):
	return

def run(core, actor, target, commandString):
	return