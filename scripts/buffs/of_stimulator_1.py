import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'debuff_purge', 2)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'debuff_purge', 2)
	
	return