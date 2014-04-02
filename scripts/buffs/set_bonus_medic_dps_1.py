import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_action_line_me_dm', 5)
	core.skillModService.addSkillMod(actor, 'expertise_action_line_me_burst', 5)
	core.skillModService.addSkillMod(actor, 'expertise_action_line_me_smash', 5)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_action_line_me_dm', 5)
	core.skillModService.deductSkillMod(actor, 'expertise_action_line_me_burst', 5)
	core.skillModService.deductSkillMod(actor, 'expertise_action_line_me_smash', 5)
	return