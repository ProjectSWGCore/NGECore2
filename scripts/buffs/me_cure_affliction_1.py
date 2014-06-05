import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'dot_reduction_all', 5)
	core.skillModService.addSkillMod(actor, 'dot_resist_all', 100)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'dot_reduction_all', 5)
	core.skillModService.deductSkillMod(actor, 'dot_resist_all', 100)
	return
	