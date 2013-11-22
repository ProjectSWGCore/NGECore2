import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_co_burst_fire_proc', 20)
	core.skillModService.addSkillMod(actor, 'expertise_devastation_bonus', 100)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_co_burst_fire_proc', 20)
	core.skillModService.deductSkillMod(actor, 'expertise_devastation_bonus', 100)
	return