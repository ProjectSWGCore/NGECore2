import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'constitution_modified', 50)
	core.skillModService.addSkillMod(actor, 'display_only_block' , 100)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 50)
	core.skillModService.deductSkillMod(actor, 'display_only_block' , 100)
	return