import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	if actor.getPosture() == 13 or actor.getPosture() == 14:
		return

	if actor.getStateBitmask() == 32768:
		actor.setStateBitmask(0)
		
	actor.setPosture(0)
	actor.setSpeedMultiplierBase(1)
	actor.setTurnRadius(1)

	return
	