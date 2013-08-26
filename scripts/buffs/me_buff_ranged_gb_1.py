import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'display_only_dodge', 500)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'display_only_dodge', 500)
	return
	