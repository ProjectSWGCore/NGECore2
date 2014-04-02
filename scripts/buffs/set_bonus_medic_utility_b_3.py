import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_cooldown_line_me_dot', 30)
	core.skillModService.addSkillMod(actor, 'expertise_buff_duration_line_me_debuff', 5)
	core.skillModService.addSkillMod(actor, 'fast_attack_line_me_debuff', 15)
	core.skillModService.addSkillMod(actor, 'expertise_freeshot_me_debuff', 15)
	core.skillModService.addSkillMod(actor, 'me_doom_chance', 20)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_cooldown_line_me_dot', 30)
	core.skillModService.deductSkillMod(actor, 'expertise_buff_duration_line_me_debuff', 5)
	core.skillModService.deductSkillMod(actor, 'fast_attack_line_me_debuff', 15)
	core.skillModService.deductSkillMod(actor, 'expertise_freeshot_me_debuff', 15)
	core.skillModService.deductSkillMod(actor, 'me_doom_chance', 20)
	return