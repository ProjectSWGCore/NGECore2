from protocol.swg.objectControllerObjects import ImageDesignStartMessage
from protocol.swg import ObjControllerMessage
import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	print ('Design!')
	design = ImageDesignStartMessage(actor.getObjectId(), actor.getObjectId(), target.getObjectId())
	obj = ObjControllerMessage(11, design)
	actor.getClient().getSession().write(obj.serialize())
	
	tDesign = ImageDesignStartMessage(target.getObjectId(), actor.getObjectId(), target.getObjectId())
	tObj = ObjControllerMessage(11, tDesign)
	target.getClient().getSession().write(tObj.serialize())
	return