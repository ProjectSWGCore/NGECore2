import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	room = core.chatService.getChatRoomByAddress('SWG.' + core.getGalaxyName() + '.' + 'Auction')
	
	if actor.getSlottedObject('ghost').isMemberOfChannel(room.getRoomId()):
		core.chatService.sendChatRoomMessage(actor, room.getRoomId(), 0, commandString)
	return