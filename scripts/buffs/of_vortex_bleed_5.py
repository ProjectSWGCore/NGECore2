import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'dot_bleed', 90)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'dot_bleed', 90)
	return