import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_dodge', 2)
	core.skillModService.addSkillMod(actor, 'constitution_modified', 20)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_dodge', 2)
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 20)
	return