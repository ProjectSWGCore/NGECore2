import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    actorGhost = actor.getSlottedObject("ghost")
    chatSrvc = core.chatService
    chatSrvc.addFriend(actorGhost, commandString, 1)
    return