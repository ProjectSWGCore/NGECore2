import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_action_line_me_dm', 10)
	core.skillModService.addSkillMod(actor, 'expertise_action_line_me_burst', 10)
	core.skillModService.addSkillMod(actor, 'expertise_action_line_me_smash', 10)
	core.skillModService.addSkillMod(actor, 'fast_attack_line_me_dm', 5)
	core.skillModService.addSkillMod(actor, 'expertise_buff_duration_line_me_enhance', 900)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_action_line_me_dm', 10)
	core.skillModService.deductSkillMod(actor, 'expertise_action_line_me_burst', 10)
	core.skillModService.deductSkillMod(actor, 'expertise_action_line_me_smash', 10)
	core.skillModService.deductSkillMod(actor, 'fast_attack_line_me_dm', 5)
	core.skillModService.deductSkillMod(actor, 'expertise_buff_duration_line_me_enhance', 900)
	return