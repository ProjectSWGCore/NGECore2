import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	print int(commandString)
	core.conversationService.handleConversationSelection(actor, int(commandString))
	return
	