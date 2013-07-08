import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	parsedMsg = commandString.split(' ', 5)
	chatService = core.chatService
	chatMsg = parsedMsg[5]
	chatType = int(parsedMsg[1])
	moodId = int(parsedMsg[2])
	chatService.handleSpatialChat(actor, target, chatMsg, chatType, moodId)
	return
	