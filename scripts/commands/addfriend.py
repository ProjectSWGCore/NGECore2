import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    actorGhost = actor.getSlottedObject("ghost")
    chatSrvc = core.chatService
    if chatSrvc and actorGhost and commandString:
	chatSrvc.addFriend(actorGhost, commandString, 1)
    return