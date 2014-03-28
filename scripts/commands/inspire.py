from protocol.swg.objectControllerObjects import BuffBuilderStartMessage
from protocol.swg import ObjControllerMessage
import sys

def setup():
    return

def run(core, actor, target, commandString):
    playerObject = actor.getSlottedObject('ghost')

    if not playerObject or playerObject.getProfession() != "entertainer_1a":
      return
    
    if target is None or actor.getObjectId() == target.getObjectId():
    	openBuffWindow = BuffBuilderStartMessage(actor.getObjectId(), actor.getObjectId(), actor.getObjectId())
    	objController = ObjControllerMessage(11, openBuffWindow)
    	actor.getClient().getSession().write(objController.serialize())
    	return
    
    if target is None and commandString is not None:
    	target = core.chatService.getObjectByFirstName(commandString)
    	if target is None:
    		return
    
    if actor.getPosture() != 0x09 and target.getObjectId() != actor.getObjectId():
      actor.sendSystemMessage('@performance:insp_buff_must_perform', 2)
      return

    if not target.getPerformanceWatchee() or target.getPerformanceWatchee() != actor:
      if actor.getPerformanceType():
        actor.sendSystemMessage('@performance:insp_buff_must_watch', 2)
        return
      else:
        actor.sendSystemMessage('@performance:insp_buff_must_listen', 2)
        return
    
    if target.getPosition().getDistance2D(actor.getWorldPosition()) > float(20):
        actor.sendSystemMessage(target.getCustomName() + ' is too far away to inspire.', 0)
        return
    
    builderWindow = BuffBuilderStartMessage(actor.getObjectId(), actor.getObjectId(), target.getObjectId())
    objController = ObjControllerMessage(11, builderWindow)
    actor.getClient().getSession().write(objController.serialize())
    
    recipientWindow = BuffBuilderStartMessage(target.getObjectId(), actor.getObjectId(), target.getObjectId())
    objController2 = ObjControllerMessage(11, recipientWindow)
    target.getClient().getSession().write(objController2.serialize())
    return
