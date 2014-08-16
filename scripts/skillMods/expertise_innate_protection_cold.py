import sys

def add(core, actor, skillMod, divisor):
	core.skillModService.addSkillMod(actor, 'cold', skillMod)
	return

def deduct(core, actor, skillMod, divisor):
	core.skillModService.deductSkillMod(actor, 'cold', skillMod)
	return
	