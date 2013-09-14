import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	actor.setSpeedMultiplierBase(float(commandString))
	core.buffService.addBuffToCreature(actor, 'bh_prescience')
	return
	