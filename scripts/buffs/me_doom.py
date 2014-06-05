import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'me_doom', 1)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'me_doom', 1)
	return	