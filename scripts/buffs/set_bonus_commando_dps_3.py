import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'fast_attack_line_co_grenade', 15)
	core.skillModService.addSkillMod(actor, 'expertise_action_line_co_grenade' , 10)
	core.skillModService.addSkillMod(actor, 'expertise_freeshot_co_grenade' , 10)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'fast_attack_line_co_grenade', 15)
	core.skillModService.deductSkillMod(actor, 'expertise_action_line_co_grenade' , 10)
	core.skillModService.deductSkillMod(actor, 'expertise_freeshot_co_grenade' , 10)
	return
