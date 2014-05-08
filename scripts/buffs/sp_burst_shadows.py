import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'movement', 12)

	return

def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'movement', 12)

	return
