import sys

def add(core, actor, skillMod, divisor):
	core.skillModService.addSkillMod(actor, 'heat', skillMod)
	return

def deduct(core, actor, skillMod, divisor):
	core.skillModService.deductSkillMod(actor, 'heat', skillMod)
	return
	