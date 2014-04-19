from resources.common import RadialOptions
from resources.datatables import Posture
import sys

def createRadial(core, owner, target, radials):
	if target.getPosture() == Posture.SkillAnimating:
		if owner.getPerformanceWatchee() == target:
			if target.getPerformanceType() is True:
				radials.add(RadialOptions(0, 141, 3, 'Stop Watching'))
		elif owner.getPerformanceListenee() == target:
			radials.add(RadialOptions(0, 141, 3, 'Stop Listening'))
		else:
			if target.getPerformanceType() is True:
				radials.add(RadialOptions(0, 140, 3, 'Watch'))
			else:
				radials.add(RadialOptions(0, 140, 3, 'Listen'))
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
	
	if option == 141:
		core.commandService.callCommand(owner, 'stopwatching', target, '')
		return
	return