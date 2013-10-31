import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'healing_health', 3500)
	core.skillModService.addSkillMod(actor, 'healing_action', 6000)
	core.skillModService.addSkillMod(actor, 'expertise_damage_all', 10)
	core.skillModService.addSkillMod(actor, 'expertise_action_all', 10)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'healing_health', 125)
	core.skillModService.deductSkillMod(actor, 'healing_action', 50)
	core.skillModService.deductSkillMod(actor, 'expertise_damage_all', 50)
	core.skillModService.deductSkillMod(actor, 'expertise_action_all', 50)
	return