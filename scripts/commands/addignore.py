import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    actorGhost = actor.getSlottedObject("ghost")
    chatSrvc = core.chatService
    
    if not actorGhost and chatSrvc and commandString:
    	return
    
    if actorGhost.getIgnoreList().contains(commandString.split(" ")[0].lower()):
    	actor.sendSystemMessage(commandString.split(' ')[0].lower() + ' is already in your ignore list.', 0)
    	return
    
    chatSrvc.addToIgnoreList(actor, commandString)
    
    return