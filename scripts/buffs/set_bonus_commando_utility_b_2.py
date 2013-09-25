import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'constitution_modified', 50)
	core.skillModService.addSkillMod(actor, 'strength_modified' , 60)
	core.skillModService.addSkillMod(actor, 'expertise_devastation_bonus' , 20)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 50)
	core.skillModService.deductSkillMod(actor, 'strength_modified' , 60)
	core.skillModService.deductSkillMod(actor, 'expertise_devastation_bonus' , 20)
	return
