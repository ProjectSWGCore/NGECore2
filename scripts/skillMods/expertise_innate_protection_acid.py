import sys

def add(core, actor, skillMod, divisor):
	core.skillModService.addSkillMod(actor, 'acid', skillMod)
	return

def deduct(core, actor, skillMod, divisor):
	core.skillModService.deductSkillMod(actor, 'acid', skillMod)
	return
	