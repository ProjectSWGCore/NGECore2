import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'precision_modified', 200)
	core.skillModService.addSkillMod(actor, 'strength_modified', 200)
	core.skillModService.addSkillMod(actor, 'expertise_on_target', 0)
	core.skillModService.addSkillMod(actor, 'movement', 0)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'movement', 10)
	core.skillModService.deductSkillMod(actor, 'precision_modified', 200)
	core.skillModService.deductSkillMod(actor, 'strength_modified', 200)
	core.skillModService.deductSkillMod(actor, 'expertise_on_target', 0)
	return