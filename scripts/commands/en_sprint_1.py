import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	core.buffService.addBuffToCreature(actor, 'en_sprint', actor)
	return