import sys

def add(core, actor, skillMod, divisor):
	core.skillModService.addSkillMod(actor, 'energy', skillMod)
	return

def deduct(core, actor, skillMod, divisor):
	core.skillModService.deductSkillMod(actor, 'energy', skillMod)
	return
	