import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'movement', 5)
	return
	
def remove(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'movement', 5)
	return