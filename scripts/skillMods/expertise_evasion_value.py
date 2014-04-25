import sys

def add(core, actor, skillMod, divisor):
	core.skillModService.addSkillMod(actor, 'combat_evasion_value', skillMod)
	return

def deduct(core, actor, skillMod, divisor):
	core.skillModService.deductSkillMod(actor, 'combat_evasion_value', skillMod)
	return
	