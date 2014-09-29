import sys

def setup(core, actor, buff):
	if actor.getSkillMod('expertise_aura_maintain'):
		if actor.getSkillModBase('expertise_aura_maintain') > 1:
			buff.setDuration(-1)
	return

def add(core, actor, buff):
	return
	
def remove(core, actor, buff):
	return