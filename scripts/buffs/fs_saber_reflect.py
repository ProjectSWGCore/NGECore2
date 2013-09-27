import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_saber_block', 1)
	
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_saber_block', 1)
	return
	