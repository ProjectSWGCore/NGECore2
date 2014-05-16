import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	core.buffService.addBuffToCreature(actor, 'co_first_aid_training', actor)
	return