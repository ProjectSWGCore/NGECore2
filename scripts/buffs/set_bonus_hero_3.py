import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'stamina_modified', 150)
	core.skillModService.addSkillMod(actor, 'agility_modified', 150)
	core.skillModService.addSkillMod(actor, 'constitution_modified', 150)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'stamina_modified', 150)
	core.skillModService.deductSkillMod(actor, 'agility_modified', 150)
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 150)
	return