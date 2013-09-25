import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'constitution_modified', 100)
	core.skillModService.addSkillMod(actor, 'strength_modified' , 125)
	core.skillModService.addSkillMod(actor, 'expertise_devastation_bonus' , 40)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 100)
	core.skillModService.deductSkillMod(actor, 'strength_modified' , 125)
	core.skillModService.deductSkillMod(actor, 'expertise_devastation_bonus' , 40)
	return
