import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_co_killing_spree_target', 1)
	
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_co_killing_spree_target', 1)
	return
	