import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'constitution_modified', 25)
	core.skillModService.addSkillMod(actor, 'strength_modified' , 25)
	core.skillModService.addSkillMod(actor, 'expertise_devastation_bonus' , 10)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 25)
	core.skillModService.deductSkillMod(actor, 'strength_modified' , 25)
	core.skillModService.deductSkillMod(actor, 'expertise_devastation_bonus' , 10)
	return
