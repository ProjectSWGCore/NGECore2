import sys

def add(core, actor, skillMod, divisor):
	core.skillModService.addSkillMod(actor, 'acid', skillMod)
	core.skillModService.addSkillMod(actor, 'heat', skillMod)
	core.skillModService.addSkillMod(actor, 'cold', skillMod)
	core.skillModService.addSkillMod(actor, 'electricity', skillMod)
	return

def deduct(core, actor, skillMod, divisor):
	core.skillModService.deductSkillMod(actor, 'acid', skillMod)
	core.skillModService.deductSkillMod(actor, 'heat', skillMod)
	core.skillModService.deductSkillMod(actor, 'cold', skillMod)
	core.skillModService.deductSkillMod(actor, 'electricity', skillMod)
	return
	