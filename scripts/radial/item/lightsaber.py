from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 21, 1, 'Open Lightsaber'))
	return
	
def handleSelection(core, owner, target, option):
	if option == 21 and target:
		core.simulationService.openContainer(owner, target.getSlottedObject("saber_inv"))
		return
	