import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_healing_all', 20)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_healing_all', 20)
	return
	