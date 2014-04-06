import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'fast_attack_line_me_heal', 10)
	core.skillModService.addSkillMod(actor, 'expertise_freeshot_me_heal', 15)
	core.skillModService.addSkillMod(actor, 'fast_attack_line_me_revive', 15)
	core.skillModService.addSkillMod(actor, 'expertise_cooldown_line_me_evasion', 30)
	core.skillModService.addSkillMod(actor, 'expertise_cooldown_line_me_stasis_self', 2)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'fast_attack_line_me_heal', 10)
	core.skillModService.deductSkillMod(actor, 'expertise_freeshot_me_heal', 15)
	core.skillModService.deductSkillMod(actor, 'fast_attack_line_me_revive', 15)
	core.skillModService.deductSkillMod(actor, 'expertise_cooldown_line_me_evasion', 30)
	core.skillModService.deductSkillMod(actor, 'expertise_cooldown_line_me_stasis_self', 2)
	return