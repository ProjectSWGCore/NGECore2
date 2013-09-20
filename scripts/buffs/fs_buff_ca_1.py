import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_stance', 1)
	
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_stance', 1)
	return
	