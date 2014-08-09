import sys

def add(core, actor, skillMod, divisor):
	core.skillModService.addSkillMod(actor, 'electricity', skillMod)
	return

def deduct(core, actor, skillMod, divisor):
	core.skillModService.deductSkillMod(actor, 'electricity', skillMod)
	return
	