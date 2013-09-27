import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'movement', 0)
	core.skillModService.addSkillMod(actor, 'invis', 0)
	
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'movement', 0)
	core.skillModService.deductSkillMod(actor, 'invis', 0)
	return
	