import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'sm_pistol_whip', 0)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'sm_pistol_whip', 0)
	return