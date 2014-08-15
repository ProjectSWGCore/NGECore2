from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 21, 1, core.questService.getQuestItemRadialName(owner, target.getTemplate()))
	radials.add(RadialOptions(0, 7, 1, ''))
	return
	
def handleSelection(core, owner, target, option):
	if option == 21 and target:
		core.questService.handleQuestItemRadialSelection(owner, target)
		return
	return