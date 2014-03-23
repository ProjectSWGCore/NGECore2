import sys

def setup(core, actor, buff):
	if actor.getSkillMod('expertise_aura_maintain'):
		buff.setDuration(-1)
	
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_dodge', 4)
	core.skillModService.addSkillMod(actor, 'constitution_modified', 50)
	core.skillModService.addSkillMod(actor, 'combat_critical_hit_reduction', 8)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_dodge', 4)
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 50)
	core.skillModService.deductSkillMod(actor, 'combat_critical_hit_reduction', 8)
	return