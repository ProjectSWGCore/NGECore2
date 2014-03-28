import sys

def setup(core, actor, buff):
	return
	
def run(core, actor, target, commandString):
	if target is None:
		target = actor
	core.buffService.addGroupBuff(target, 'of_drillmaster_1', actor)
	return
	