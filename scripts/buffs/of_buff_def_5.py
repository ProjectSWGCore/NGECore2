import sys

def setup(core, actor, buff):
	if actor.getSkillMod('expertise_aura_maintain'):
		buff.setDuration(-1)
	
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_dodge', 3)
	core.skillModService.addSkillMod(actor, 'constitution_modified', 25)
	core.skillModService.addSkillMod(actor, 'combat_critical_hit_reduction', 6)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_dodge', 3)
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 25)
	core.skillModService.deductSkillMod(actor, 'combat_critical_hit_reduction', 6)
	return