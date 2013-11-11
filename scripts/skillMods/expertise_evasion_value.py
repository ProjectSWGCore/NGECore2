import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	core.skillModService.addSkillMod(actor, 'combat_evasion_value', (actor.getSkillModBase('expertise_evasion_value')))
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	core.skillModService.deductSkillMod(actor, 'combat_evasion_value', (actor.getSkillModBase('expertise_evasion_value')))
	return
	