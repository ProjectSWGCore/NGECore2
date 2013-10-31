import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_damage_all', 10)
	core.skillModService.addSkillMod(actor, 'expertise_action_all', 10)
	core.skillModService.addSkillMod(actor, 'movement_resist_snare', 100)
	actor.setSpeedMultiplierBase(1.5)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_damage_all', 10)
	core.skillModService.deductSkillMod(actor, 'expertise_action_all', 10)
	core.skillModService.deductSkillMod(actor, 'movement_resist_snare', 100)
	actor.setSpeedMultiplierBase(1)
	return
	