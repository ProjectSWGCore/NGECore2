import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	if actor.getGroupId() == 0:
		return
		
	group = core.objectService.getObject(actor.getGroupId())
	
	for creature in group.getMemberList():
		if creature.getPosture() == 14:
			core.simulationService.teleport(creature, actor.getPosition(), actor.getOrientation(), actor.getParentId())

	return
	