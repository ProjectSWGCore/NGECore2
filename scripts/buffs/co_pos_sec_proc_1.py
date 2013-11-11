import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_co_burst_fire_proc', 10)
	core.skillModService.addSkillMod(actor, 'expertise_devastation_bonus', 50)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_co_burst_fire_proc', 10)
	core.skillModService.deductSkillMod(actor, 'expertise_devastation_bonus', 50)
	return