import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	#actor.sendSystemMessage('Cell ID %s' % actor.getContainer().getTemplate(), 0)
	#actor.sendSystemMessage('Cell ID ' + actor.getContainer().getTemplate(), 0)
	#actor.sendSystemMessage('CellNumber: ' + str(actor.getGrandparent().getCellNumberByObjectId(actor.getContainer().getObjectID())), 0)
	actor.sendSystemMessage('CellNumber: ' + str(actor.getContainer().getObjectID()), 0)
	if actor.getPosture() == 13 or actor.getPosture() == 14:
		return

	actor.setPosture(1)
	actor.setSpeedMultiplierBase(0)
	actor.setTurnRadius(0)
	return
	