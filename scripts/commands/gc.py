import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	group = core.objectService.getObject(actor.getGroupId())
	
	if group is None:
		return
	
	core.chatService.sendChatRoomMessage(actor, group.getChatRoomId(), commandString)
	return