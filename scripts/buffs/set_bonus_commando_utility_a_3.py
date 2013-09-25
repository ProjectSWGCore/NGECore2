import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'constitution_modified', 150)
	core.skillModService.addSkillMod(actor, 'display_only_block' , 300)
	core.skillModService.addSkillMod(actor, 'combat_block_value' , 500)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 150)
	core.skillModService.deductSkillMod(actor, 'display_only_block' , 300)
	core.skillModService.deductSkillMod(actor, 'combat_block_value' , 500)
	return
