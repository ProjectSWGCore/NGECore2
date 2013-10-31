import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_damage_line_of_aoe', 125)
	core.skillModService.addSkillMod(actor, 'expertise_critical_line_of_aoe', 50)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_damage_line_of_aoe', 125)
	core.skillModService.deductSkillMod(actor, 'expertise_critical_line_of_aoe', 50)
	return