import sys
from resources.datatables import Posture

def setup():
	return
	
def run(core, actor, target, commandString):

	if actor.getPosture() == Posture.Incapacitated or actor.getPosture() == Posture.Dead:
		return

	actor.setPosture(Posture.Crouched)
	actor.setSpeedMultiplierBase(0)
	actor.setTurnRadius(0)

	return
	