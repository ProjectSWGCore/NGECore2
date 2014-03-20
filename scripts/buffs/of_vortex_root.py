import sys

def setup(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'movement', 10)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'movement', 10)
	return