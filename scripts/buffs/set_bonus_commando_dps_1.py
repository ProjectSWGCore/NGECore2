import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'fast_attack_line_co_grenade', 5)
	core.skillModService.addSkillMod(actor, 'expertise_action_line_co_grenade', 5)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'fast_attack_line_co_grenade', 10)
	core.skillModService.deductSkillMod(actor, 'expertise_action_line_co_grenade', 5)
	return
