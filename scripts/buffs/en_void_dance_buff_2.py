import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_glancing_blow_all', 70)
	
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_glancing_blow_all', 70)
	return