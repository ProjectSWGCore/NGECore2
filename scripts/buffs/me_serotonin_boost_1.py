import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'debuff_purge', 1)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'debuff_purge', 1)
	return