import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'glancing_blow_vulnerable', 15)
	core.skillModService.addSkillMod(actor, 'movement', -5)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'glancing_blow_vulnerable', 15)
	core.skillModService.deductSkillMod(actor, 'movement', 5)
	return
	