import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'action_regen', 1000)
	core.skillModService.addSkillMod(actor, 'movement', 2)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'action_regen', 1000)
	core.skillModService.deductSkillMod(actor, 'movement', 2)
	return
	
