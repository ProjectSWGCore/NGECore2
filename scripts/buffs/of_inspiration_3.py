import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_action_all', 5)
	core.skillModService.addSkillMod(actor, 'healing_action', 1532)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_action_all', 5)
	core.skillModService.deductSkillMod(actor, 'healing_action', 1532)
	return