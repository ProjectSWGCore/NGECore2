import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'dot_bleed', 72)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'dot_bleed', 72)
	return