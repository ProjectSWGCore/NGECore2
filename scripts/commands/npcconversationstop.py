import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	if actor.getConversingNpc():
		core.conversationService.handleEndConversation(actor, actor.getConversingNpc())
	return
	