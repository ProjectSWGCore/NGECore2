import sys
from resources.objects import SWGObject
from main import NGECore

def setup():
	return
	
def run(core, actor, target, commandString):
	actor.setPosture(2)
	actor.setSpeedMultiplierBase(0.25)
	actor.setTurnRadius(1)
	return
	