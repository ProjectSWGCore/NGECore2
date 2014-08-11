import sys

def setup(core, actor, buff):
	
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'strength_modified', 100)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'strength_modified', 100)
	return
	