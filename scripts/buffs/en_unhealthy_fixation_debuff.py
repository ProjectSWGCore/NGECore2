import sys

def setup(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'en_unhealthy_stun', 1)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'en_unhealthy_stun', 1)
	return