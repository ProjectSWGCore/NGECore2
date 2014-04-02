from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 21, 1, ''))
	radials.add(RadialOptions(0, 7, 1, ''))
	radials.add(RadialOptions(0, 26, 1, ''))
	return
	
def handleSelection(core, owner, target, option):
	if option == 26 and target:
		print 'test'
		core.conversationService.handleStartConversation(owner, target)
	return
	