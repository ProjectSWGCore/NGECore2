import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'movement', float(-0.5))
	
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'movement', float(-0.5))
	return
	