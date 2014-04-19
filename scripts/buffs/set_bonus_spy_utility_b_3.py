import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_strikethrough_chance', 3)
	core.skillModService.addSkillMod(actor, 'expertise_action_all', 15)
	core.skillModService.addSkillMod(actor, 'expertise_avoidance_overpower', 5)
	core.skillModService.addSkillMod(actor, 'movement_resist_snare', 100)
	core.skillModService.addSkillMod(actor, 'movement_resist_root', 100)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_strikethrough_chance', 3)
	core.skillModService.deductSkillMod(actor, 'expertise_action_all', 15)
	core.skillModService.deductSkillMod(actor, 'expertise_avoidance_overpower', 5)
	core.skillModService.deductSkillMod(actor, 'movement_resist_snare', 100)
	core.skillModService.deductSkillMod(actor, 'movement_resist_root', 100)
	return