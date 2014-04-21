import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	group = core.objectService.getObject(actor.getGroupId())
	
	if group is None:
		return

	room = core.chatService.getChatRoom(group.getChatRoomId())
	
	if room is None:
		return

	core.chatService.sendChatRoomMessage(actor, room.getRoomId(), 0, commandString)
	return