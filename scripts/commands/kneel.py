import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	if actor.getPosture() == 13 or actor.getPosture() == 14:
		return

	actor.setPosture(1)
	actor.setSpeedMultiplierBase(0)
	actor.setTurnRadius(0)
	return
	