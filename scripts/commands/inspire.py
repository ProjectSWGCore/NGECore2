from protocol.swg.objectControllerObjects import BuffBuilderStartMessage
from protocol.swg import ObjControllerMessage
import sys

def setup():
    return

def run(core, actor, target, commandString):
    playerObject = actor.getSlottedObject('ghost')
    print playerObject.getProfession()
    if not playerObject or playerObject.getProfession() != "entertainer_1a":
      return
    
    if target is None or actor.getObjectId() == target.getObjectId():
    	print ('Buffing Player: ' + str(target.getObjectId()) + ' or: ' + target.getCustomName())
    	openBuffWindow = BuffBuilderStartMessage(actor.getObjectId(), actor.getObjectId(), actor.getObjectId())
    	objController = ObjControllerMessage(11, openBuffWindow)
    	actor.getClient().getSession().write(objController.serialize())
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

    print ('Buffing Player: ' + str(target.getObjectId()) + ' or: ' + target.getCustomName())
    openBuffWindow = BuffBuilderStartMessage(actor.getObjectId(), actor.getObjectId(), target.getObjectId())
    objController = ObjControllerMessage(11, openBuffWindow)
    actor.getClient().getSession().write(objController.serialize())
    
    openBuffWindow = BuffBuilderStartMessage(target.getObjectId(), actor.getObjectId(), target.getObjectId())
    objController2 = ObjControllerMessage(11, openBuffWindow)
    target.getClient().getSession().write(objController2.serialize())
    return
