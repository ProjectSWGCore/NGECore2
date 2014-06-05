import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'movement', 6)
	core.skillModService.addSkillMod(actor, 'movement_resist_snare', 100)

	return

def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'movement', 6)
	core.skillModService.deductSkillMod(actor, 'movement_resist_snare', 100)

	return