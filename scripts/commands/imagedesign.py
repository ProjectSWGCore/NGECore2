from protocol.swg.objectControllerObjects import ImageDesignStartMessage
from protocol.swg import ObjControllerMessage
import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	if target is None and commandString is None:
		design = ImageDesignStartMessage(actor.getObjectId(), actor.getObjectId(), actor.getObjectId())
		obj = ObjControllerMessage(11, design)
		actor.getClient().getSession().write(obj.serialize())
		return
	
	else:
		if target is None and commandString is not None:
			target = core.chatService.getObjectByFirstName(commandString)
			if target is None:
				return

		if target.getGroupId() == actor.getGroupId():

			tDesign = ImageDesignStartMessage(target.getObjectId(), actor.getObjectId(), target.getObjectId())
			tObj = ObjControllerMessage(11, tDesign)
			target.getClient().getSession().write(tObj.serialize())
			
			design = ImageDesignStartMessage(actor.getObjectId(), actor.getObjectId(), target.getObjectId())
			obj = ObjControllerMessage(11, design)
			actor.getClient().getSession().write(obj.serialize())
			return
		return
	return