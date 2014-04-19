from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.clear()	
	radials.add(RadialOptions(0, 7, 0, 'Examine'))
	radials.add(RadialOptions(0, 15, 0, 'Destroy'))
	radials.add(RadialOptions(0, 19, 0, 'Tune'))
	return
	
def handleSelection(core, owner, target, option):
	if option == 19 and target:
		if owner is not None:	
			owner.sendSystemMessage('You are not attuned enough with the force yet.',1)
			return
