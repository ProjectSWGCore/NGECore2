import sys

def add(core, actor, skillMod, value):
	core.skillModService.addSkillMod(actor, 'display_only_parry', skillMod.getBase() * 100)
	return

def deduct(core, actor, skillMod, value):
	core.skillModService.deductSkillMod(actor, 'display_only_parry', skillMod.getBase() * 100)
	return
	