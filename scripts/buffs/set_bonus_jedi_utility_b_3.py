import sys
	
def setup(core, actor, buff):
	return
	
def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'constitution_modified', 150)
	core.skillModService.addSkillMod(actor, 'combat_critical_hit_reduction', 5)
	core.skillModService.addSkillMod(actor, 'display_only_expertise_critical_hit_reduction', 500)
	core.skillModService.addSkillMod(actor, 'expertise_action_all', 10)
	#actor.addAbility("fs_set_heroic_taunt_1")
	return
		
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 150)
	core.skillModService.deductSkillMod(actor, 'combat_critical_hit_reduction', 5)
	core.skillModService.deductSkillMod(actor, 'display_only_expertise_critical_hit_reduction', 500)
	core.skillModService.deductSkillMod(actor, 'expertise_action_all', 10)
	#actor.removeAbility("fs_set_heroic_taunt_1")
	return
