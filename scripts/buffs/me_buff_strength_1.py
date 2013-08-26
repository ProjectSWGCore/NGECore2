import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'strength_modified', 15)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'strength_modified', 15)
	return
	