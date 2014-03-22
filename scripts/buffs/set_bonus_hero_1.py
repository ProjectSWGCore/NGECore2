import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'stamina_modified', 60)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'stamina_modified', 60)
	return