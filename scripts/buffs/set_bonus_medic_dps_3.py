import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_action_line_me_dm', 15)
	core.skillModService.addSkillMod(actor, 'expertise_action_line_me_burst', 20)
	core.skillModService.addSkillMod(actor, 'expertise_action_line_me_smash', 20)
	core.skillModService.addSkillMod(actor, 'fast_attack_line_me_dm', 15)
	core.skillModService.addSkillMod(actor, 'expertise_buff_duration_line_me_enhance', 1800)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_action_line_me_dm', 15)
	core.skillModService.deductSkillMod(actor, 'expertise_action_line_me_burst', 20)
	core.skillModService.deductSkillMod(actor, 'expertise_action_line_me_smash', 20)
	core.skillModService.deductSkillMod(actor, 'fast_attack_line_me_dm', 15)
	core.skillModService.deductSkillMod(actor, 'expertise_buff_duration_line_me_enhance', 1800)
	return