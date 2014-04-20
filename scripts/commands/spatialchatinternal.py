import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	parsedMsg = commandString.split(' ', 5)
	chatService = core.chatService
	chatMsg = parsedMsg[5]
	chatType = int(parsedMsg[1])
	moodId = int(parsedMsg[2])
	languageId = int(parsedMsg[4])
	chatService.spatialChat(actor, target, chatMsg, chatType, moodId, languageId, None)
	return
	