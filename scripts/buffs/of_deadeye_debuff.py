import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'action_burn', 50)
	core.skillModService.addSkillMod(actor, 'glancing_blow_vulnerable', 30)
	actor.setSpeedMultiplierBase(1.5)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_dodge', 50)
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 30)
	actor.setSpeedMultiplierBase(1)
	return