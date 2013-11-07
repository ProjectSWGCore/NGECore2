import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'sm_pistol_whip', 0)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'sm_pistol_whip', 0)
	return