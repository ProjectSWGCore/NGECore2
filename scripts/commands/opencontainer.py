import sys
from resources.objects import SWGObject
from main import NGECore

def setup():
	return
	
def run(core, actor, target, commandString):
	if target:
		simService = core.simulationService
		simService.openContainer(actor, target)
	return
	