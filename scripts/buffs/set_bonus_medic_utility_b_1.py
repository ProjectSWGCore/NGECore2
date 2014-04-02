import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_cooldown_line_me_dot', 10)
	core.skillModService.addSkillMod(actor, 'expertise_buff_duration_line_me_debuff', 1)
	core.skillModService.addSkillMod(actor, 'fast_attack_line_me_debuff', 5)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_cooldown_line_me_dot', 10)
	core.skillModService.deductSkillMod(actor, 'expertise_buff_duration_line_me_debuff', 1)
	core.skillModService.deductSkillMod(actor, 'fast_attack_line_me_debuff', 5)
	return