import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	room = core.chatService.getChatRoomByAddress('SWG.' + core.getGalaxyName() + '.' + actor.getPlanet().getName() + '.Planet')
	
	if room is None:
		return
	
	core.chatService.sendChatRoomMessage(actor, room.getRoomId(), 0, commandString)
	return