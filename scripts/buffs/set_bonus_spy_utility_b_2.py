import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_strikethrough_chance', 2)
	core.skillModService.addSkillMod(actor, 'expertise_action_all', 10)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_strikethrough_chance', 2)
	core.skillModService.deductSkillMod(actor, 'expertise_action_all', 10)
	return