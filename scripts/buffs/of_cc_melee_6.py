import sys

def setup(core, actor, buff):
	actor.setSpeedMultiplierBase(0.5)
	return
	
def removeBuff(core, actor, buff):
	actor.setSpeedMultiplierBase(1)
	return