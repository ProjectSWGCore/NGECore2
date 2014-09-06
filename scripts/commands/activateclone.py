import sys
from resources.datatables import Posture
from services import PlayerService

def setup():
    return
    
def run(core, actor, target, commandString):
	if actor.getPosture() == Posture.Dead:
		core.playerService.sendCloningWindow(actor, 1)
	return