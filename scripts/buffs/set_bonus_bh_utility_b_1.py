import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'fast_attack_line_dm_cc', 5)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'fast_attack_line_dm_cc', 5)
	return