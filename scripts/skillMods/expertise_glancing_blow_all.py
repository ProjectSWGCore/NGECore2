import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	core.skillModService.addSkillMod(actor, 'display_only_glancing_blow', 100 * (actor.getSkillModBase('expertise_glancing_blow_all')))
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	core.skillModService.deductSkillMod(actor, 'display_only_glancing_blow', 100 * (actor.getSkillModBase('expertise_glancing_blow_all')))
	return
	