import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_dodge', 2)
	core.skillModService.addSkillMod(actor, 'constitution_modified', 20)
	core.skillModService.addSkillMod(actor, 'combat_critical_hit_reduction', 5)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_dodge', 2)
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 20)
	core.skillModService.deductSkillMod(actor, 'combat_critical_hit_reduction', 5)
	return