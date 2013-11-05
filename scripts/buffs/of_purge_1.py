import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'dot_divisor_all', 50)
	core.skillModService.addSkillMod(actor, 'dot_resist_all', 100)

	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'dot_divisor_all', 50)
	core.skillModService.deductSkillMod(actor, 'dot_resist_all', 100)
	return