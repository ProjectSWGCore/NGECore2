import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_cooldown_line_me_dot', 20)
	core.skillModService.addSkillMod(actor, 'expertise_buff_duration_line_me_debuff', 3)
	core.skillModService.addSkillMod(actor, 'fast_attack_line_me_debuff', 10)
	core.skillModService.addSkillMod(actor, 'expertise_freeshot_me_debuff', 10)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_cooldown_line_me_dot', 20)
	core.skillModService.deductSkillMod(actor, 'expertise_buff_duration_line_me_debuff', 3)
	core.skillModService.deductSkillMod(actor, 'fast_attack_line_me_debuff', 10)
	core.skillModService.deductSkillMod(actor, 'expertise_freeshot_me_debuff', 10)
	return