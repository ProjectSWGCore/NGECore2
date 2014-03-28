import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_damage_all', 10)
	core.skillModService.addSkillMod(actor, 'expertise_glancing_blow_ranged', 40)
	core.skillModService.addSkillMod(actor, 'invis', 1)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_damage_all', 10)
	core.skillModService.deductSkillMod(actor, 'expertise_glancing_blow_ranged', 40)
	core.skillModService.deductSkillMod(actor, 'invis', 1)
	return
	