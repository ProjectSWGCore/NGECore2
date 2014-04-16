from main import NGECore
import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	room = core.chatService.getChatRoomByAddress('SWG.' + NGECore.getInstance().getGalaxyName() + '.' + 'Auction')
	core.chatService.sendChatRoomMessage(actor, room.getRoomId(), 0, commandString)
	return