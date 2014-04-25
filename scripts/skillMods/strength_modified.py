import sys

def add(core, actor, skillMod, divisor):
	core.skillModService.addSkillMod(actor, 'display_only_block', skillMod.getBase() / 2)
	return

def deduct(core, actor, name, base):
	core.skillModService.deductSkillMod(actor, 'display_only_block', skillMod.getBase() / 2)
	return
	