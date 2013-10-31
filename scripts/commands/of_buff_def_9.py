import sys

def setup(core, actor, buff):
	return
	
def run(core, actor, target, commandString):
	core.buffService.addBuffToCreature(actor, 'of_buff_def_9')
	group = core.objectService.getObject(actor.getGroupId())
	for creature in group.getMemberList():
		core.buffService.addBuffToCreature(creature, 'of_buff_def_9')
	return
	