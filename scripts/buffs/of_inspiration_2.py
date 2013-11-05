import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_action_all', 5)
	core.skillModService.addSkillMod(actor, 'healing_action', 1111)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_action_all', 5)
	core.skillModService.deductSkillMod(actor, 'healing_action', 1111)
	return