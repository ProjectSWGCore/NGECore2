import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'precision_modified', 80)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'precision_modified', 80)
	return
	