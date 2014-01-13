import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'buff_purge', 1)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'buff_purge', 1)
	return