import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	core.skillModService.addSkillMod(actor, 'constitution_modified', (actor.getSkillModBase('expertise_stance_constitution')))
	core.skillModService.addSkillMod(actor, 'display_only_parry', 100 * (actor.getSkillModBase('expertise_stance_parry')))
	core.skillModService.addSkillMod(actor, 'display_only_evasion', 100 *(actor.getSkillModBase('expertise_stance_evasion')))
	
	
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	core.skillModService.deductSkillMod(actor, 'constitution_modified', (actor.getSkillModBase('expertise_stance_constitution')))
	core.skillModService.deductSkillMod(actor, 'display_only_parry', 100 * (actor.getSkillModBase('expertise_stance_parry')))
	core.skillModService.deductSkillMod(actor, 'display_only_evasion', 100 * (actor.getSkillModBase('expertise_stance_evasion')))
	return
	