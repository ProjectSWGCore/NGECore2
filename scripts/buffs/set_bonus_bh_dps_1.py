import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_action_line_dm', 5)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_action_line_dm', 5)
	return