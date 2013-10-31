import sys

def setup(core, actor, buff):
	return
	
def run(core, actor, target, commandString):
	core.buffService.addBuffToCreature(actor, 'of_focus_fire_1')
	group = core.objectService.getObject(actor.getGroupId())
	for creature in group.getMemberList():
		core.buffService.addBuffToCreature(creature, 'of_focus_fire_3')
	return