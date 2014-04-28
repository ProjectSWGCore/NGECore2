import sys

def add(core, actor, skillMod, divisor):
	core.skillModService.addSkillMod(actor, 'display_only_evasion', 100 * skillMod.getBase())
	return

def deduct(core, actor, skillMod, divisor):
	core.skillModService.deductSkillMod(actor, 'display_only_evasion', 100 * skillMod.getBase())
	return
	