import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_strikethrough_chance', 1)
	core.skillModService.addSkillMod(actor, 'expertise_action_all', 5)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_strikethrough_chance', 1)
	core.skillModService.deductSkillMod(actor, 'expertise_action_all', 5)
	return