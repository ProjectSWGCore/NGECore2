import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'buff_purge', 1)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'buff_purge', 1)
	return