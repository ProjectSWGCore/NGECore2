import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'display_only_block', 1000)
	core.skillModService.addSkillMod(actor, 'combat_block_value', 200)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'display_only_block', 1000)
	core.skillModService.deductSkillMod(actor, 'combat_block_value', 200)	
	return
	