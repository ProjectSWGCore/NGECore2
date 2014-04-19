import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_cooldown_line_sp_preparation', 60)
	core.skillModService.addSkillMod(actor, 'expertise_buff_duration_line_sp_preparation', 10)
	core.skillModService.addSkillMod(actor, 'fast_attack_line_sp_smoke', 20)
	core.skillModService.addSkillMod(actor, 'fast_attack_line_sp_perfect', 15)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_cooldown_line_sp_preparation', 60)
	core.skillModService.deductSkillMod(actor, 'expertise_buff_duration_line_sp_preparation', 10)
	core.skillModService.deductSkillMod(actor, 'fast_attack_line_sp_smoke', 20)
	core.skillModService.deductSkillMod(actor, 'fast_attack_line_sp_perfect', 15)
	return