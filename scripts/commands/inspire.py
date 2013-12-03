from protocol.swg.objectControllerObjects import BuffBuilderStartMessage
from protocol.swg import ObjControllerMessage
import sys

def setup():
    return

def run(core, actor, target, commandString):
    
    if (actor.getProfession() != "entertainer_1a"):
        return
    
    print ('Buffing Player: ' + str(target.getObjectId()) + ' or: ' + target.getCustomName())
    openBuffWindow = BuffBuilderStartMessage(actor.getObjectId(), actor.getObjectId(), target.getObjectId())
    objController = ObjControllerMessage(11, openBuffWindow)
    actor.getClient().getSession().write(objController.serialize())
    
    penBuffWindow = BuffBuilderStartMessage(target.getObjectId(), actor.getObjectId(), target.getObjectId())
    objController2 = ObjControllerMessage(11, penBuffWindow)
    target.getClient().getSession().write(objController2.serialize())
    return