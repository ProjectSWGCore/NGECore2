from protocol.swg.objectControllerObjects import BuffBuilderStartMessage
from protocol.swg import ObjControllerMessage
import sys

def setup():
    return

def run(core, actor, target, commandString):
    print ('Buffing Player: ' + str(target.getObjectId()))
    openBuffWindow = BuffBuilderStartMessage(actor.getObjectId(), target.getObjectId())
    objController = ObjControllerMessage(11, openBuffWindow)
    actor.getClient().getSession().write(objController.serialize())
    return