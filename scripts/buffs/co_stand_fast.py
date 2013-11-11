import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_damage_decrease_percentage', 0)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_damage_decrease_percentage', 0)
	return