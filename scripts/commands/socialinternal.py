import sys
from resources.objects import SWGObject
from main import NGECore

def setup():
	return
	
def run(core, actor, target, commandString):
	chatService = core.chatService
	parsedMsg = commandString.split(' ', 3)
	targetId = long(parsedMsg[0])
	target = core.objectService.getObject(targetId)
	emoteId = int(parsedMsg[1])
	chatService.handleEmote(actor, target, emoteId)

	return

	
	

