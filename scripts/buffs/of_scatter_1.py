import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_glancing_blow_all', 2)
	core.skillModService.addSkillMod(actor, 'movement_resist_snare', 15)
	core.skillModService.addSkillMod(actor, 'movement_resist_root', 2)
	actor.setSpeedMultiplierBase(1.5)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_glancing_blow_all', 2)
	core.skillModService.deductSkillMod(actor, 'movement_resist_snare', 15)
	core.skillModService.deductSkillMod(actor, 'movement_resist_root', 2)
	actor.setSpeedMultiplierBase(1)
	return