import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_dodge', 35)
	core.skillModService.addSkillMod(actor, 'expertise_evasion_chance', 50)
	core.skillModService.addSkillMod(actor, 'combat_evasion_value', 50)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_dodge', 35)
	core.skillModService.deductSkillMod(actor, 'expertise_evasion_chance', 50)
	core.skillModService.deductSkillMod(actor, 'combat_evasion_value', 50)
	return
	