import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_riddle_armor', 0)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_riddle_armor', 0)
	return