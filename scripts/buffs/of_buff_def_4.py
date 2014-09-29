import sys

def setup(core, actor, buff):
	if actor.getSkillMod('expertise_aura_maintain'):
		buff.setDuration(-1)
	
	return

def add(core, actor, buff):
	return
	
def remove(core, actor, buff):
	return