import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_dodge', -4)
	core.skillModService.addSkillMod(actor, 'expertise_parry', -4)
	core.skillModService.addSkillMod(actor, 'critical_hit_vulnerable', 4)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_dodge', -4)
	core.skillModService.deductSkillMod(actor, 'expertise_parry', -4)
	core.skillModService.deductSkillMod(actor, 'critical_hit_vulnerable', 4)
	return