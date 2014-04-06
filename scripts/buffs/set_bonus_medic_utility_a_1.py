import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_freeshot_me_heal', 5)
	core.skillModService.addSkillMod(actor, 'fast_attack_line_me_revive', 5)
	core.skillModService.addSkillMod(actor, 'expertise_cooldown_line_me_evasion', 10)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_freeshot_me_heal', 5)
	core.skillModService.deductSkillMod(actor, 'fast_attack_line_me_revive', 5)
	core.skillModService.deductSkillMod(actor, 'expertise_cooldown_line_me_evasion', 10)
	return