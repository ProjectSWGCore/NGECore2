import sys
	
def setup(core, actor, buff):
	return
	
def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'constitution_modified', 25)
	core.skillModService.addSkillMod(actor, 'combat_critical_hit_reduction', 1)
	core.skillModService.addSkillMod(actor, 'display_only_expertise_critical_hit_reduction', 100)
	return
		
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 25)
	core.skillModService.deductSkillMod(actor, 'combat_critical_hit_reduction', 1)
	core.skillModService.deductSkillMod(actor, 'display_only_expertise_critical_hit_reduction', 100)
	return
