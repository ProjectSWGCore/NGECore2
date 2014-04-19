import sys
from protocol.swg.objectControllerObjects import BiographyUpdate
from protocol.swg import ObjControllerMessage

def setup():
	return
	
def run(core, actor, target, commandString):
	if actor is None:
		return
	
	if len(commandString) > 200:
		return
	
	ghost = actor.getSlottedObject('ghost')
	
	if ghost is None:
		return
	
	ghost.setBiography(commandString)
	
	bioUpdate = BiographyUpdate(actor.getObjectId(), actor.getObjectId(), commandString)
	objMsg = ObjControllerMessage(11, bioUpdate)
	actor.getClient().getSession().write(objMsg.serialize())
	return