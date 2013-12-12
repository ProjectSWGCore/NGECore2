import sys

def setup(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'action_burn', 65)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'action_burn', 65)
	return
	