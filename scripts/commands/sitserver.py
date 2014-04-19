import sys
from protocol.swg.objectControllerObjects import SitOnObject
from protocol.swg import ObjControllerMessage

def setup():
	return
	
def run(core, actor, target, commandString):

	if actor.getPosture() == 13 or actor.getPosture() == 14:
		return

	if len(commandString) > 0:
		parsedMsg = commandString.split(',', 4)
		x = float(parsedMsg[0])
		y = float(parsedMsg[1])
		z = float(parsedMsg[2])
		cellId = long(parsedMsg[3])
		sitOnObject = SitOnObject(x, y, z, cellId, actor.getObjectID())
		objController = ObjControllerMessage(27, sitOnObject)
		actor.notifyObservers(objController, True)
		actor.setPosture(8)
		actor.setStateBitmask(32768)
		actor.setTurnRadius(0)
		return
	# sit w/o chair
	actor.setPosture(8)
	actor.setSpeedMultiplierBase(0)
	actor.setTurnRadius(0)
	return
	