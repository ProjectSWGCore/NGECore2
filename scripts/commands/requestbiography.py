import sys
from protocol.swg.objectControllerObjects import BiographyUpdate
from protocol.swg import ObjControllerMessage


# Called when open Character tab
def setup():
	return
	
def run(core, actor, target, commandString):
	
	if actor is None:
		return
	
	if target is None:
		return
	
	tGhost = target.getSlottedObject('ghost')
	
	bioUpdate = BiographyUpdate(actor.getObjectId(), target.getObjectId(), tGhost.getBiography())
	objMsg = ObjControllerMessage(11, bioUpdate)
	actor.getClient().getSession().write(objMsg.serialize())
	
	return