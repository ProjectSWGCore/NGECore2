import sys
from resources.objects import SWGObject
from main import NGECore

def setup():
	return
	
def run(core, actor, target, commandString):

	if actor.getStateBitmask() == 32768:
		actor.setStateBitmask(0)
		
	actor.setPosture(0)
	actor.setSpeedMultiplierBase(1)
	actor.setTurnRadius(1)

	return
	