import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    actorGhost = actor.getSlottedObject("ghost")
    
    chatSrvc = core.chatService
    if chatSrvc and actorGhost and commandString:
    	if actorGhost.getFriendList().contains(commandString.split(" ")[0].lower()):
    		actor.sendSystemMessage(commandString.split(" ")[0].lower() + ' is already in your friend list.', 0)
    		return
    	else:
    		chatSrvc.addFriend(actorGhost, commandString)
    return