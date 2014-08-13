import sys

def setup(core, actor, buff):
	
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'constitution_modified', 100)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 100)
	return
	