import sys

def setup(core, actor, buff):
	return
	
def run(core, actor, target, commandString):
	if target is None:
		target = actor
	core.buffService.addGroupBuff(target, 'of_focus_fire_2', actor)
	return
	