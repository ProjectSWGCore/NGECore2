import sys
	
def setup(core, actor, buff):
	return
	
def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'constitution_modified', 50)
	core.skillModService.addSkillMod(actor, 'combat_critical_hit_reduction', 3)
	core.skillModService.addSkillMod(actor, 'display_only_expertise_critical_hit_reduction', 300)
	core.skillModService.addSkillMod(actor, 'expertise_action_all', 5)
	return
		
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 50)
	core.skillModService.deductSkillMod(actor, 'combat_critical_hit_reduction', 3)
	core.skillModService.deductSkillMod(actor, 'display_only_expertise_critical_hit_reduction', 300)
	core.skillModService.deductSkillMod(actor, 'expertise_action_all', 5)
	return
