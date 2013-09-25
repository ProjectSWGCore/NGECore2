import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'constitution_modified', 75)
	core.skillModService.addSkillMod(actor, 'display_only_block' , 200)
	core.skillModService.addSkillMod(actor, 'combat_block_value' , 200)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 75)
	core.skillModService.deductSkillMod(actor, 'display_only_block' , 200)
	core.skillModService.deductSkillMod(actor, 'combat_block_value' , 200)
	return