from resources.common import RadialOptions
from resources.datatables import Posture
import sys

def createRadial(core, owner, target, radials):
	if target.getPosture() == Posture.SkillAnimating:
		if target.getPerformanceType() is True:
			radials.add(RadialOptions(0, 140, 1, 'Watch'))
		else:
			radials.add(RadialOptions(0, 140, 1, 'Listen'))

	return
	
def handleSelection(core, owner, target, option):
	if option == 140:
		if target.getPerformanceType() is True:
			core.commandService.callCommand(owner, 'watch', target, '')
			return
		else:
			# TODO: Insert callCommand /listen
			return
		return
	return