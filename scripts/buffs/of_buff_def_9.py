import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_dodge', 5)
	core.skillModService.addSkillMod(actor, 'constitution_modified', 70)
	core.skillModService.addSkillMod(actor, 'combat_critical_hit_reduction', 10)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_dodge', 5)
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 70)
	core.skillModService.deductSkillMod(actor, 'combat_critical_hit_reduction', 10)
	return