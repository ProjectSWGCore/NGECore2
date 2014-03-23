import sys

def setup(core, actor, buff):
	return
	
def run(core, actor, target, commandString):

	group = core.objectService.getObject(actor.getGroupId())
	
	if target:
		if group and target in group.getMemberList():
			core.buffService.addGroupBuff(actor, 'me_buff_ranged_gb_1')
		else:
			core.buffService.addBuffToCreature(target, 'me_buff_ranged_gb_1')
	else:
		core.buffService.addGroupBuff(actor, 'me_buff_ranged_gb_1')
		
	return
	