import sys

def setup(core, actor, buff):
	
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_critical_niche_all', 5)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_critical_niche_all', 5)
	return
	