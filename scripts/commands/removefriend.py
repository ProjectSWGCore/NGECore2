import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    ghost = actor.getSlottedObject('ghost')
    chatSvc = core.chatService
    chatSvc.removeFriend(ghost, commandString, 1)
    return
    