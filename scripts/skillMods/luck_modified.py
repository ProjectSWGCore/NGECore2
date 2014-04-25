import sys

def add(core, actor, skillMod, divisor):
	core.skillModService.addSkillMod(actor, 'display_only_dodge', skillMod.getBase() / 3)
	core.skillModService.addSkillMod(actor, 'display_only_evasion', skillMod.getBase() / 3)
	core.skillModService.addSkillMod(actor, 'display_only_critical', skillMod.getBase() / 3)
	core.skillModService.addSkillMod(actor, 'display_only_strikethrough', skillMod.getBase() / 2)
	return

def deduct(core, actor, skillMod, divisor):
	core.skillModService.deductSkillMod(actor, 'display_only_dodge', skillMod.getBase() / 3)
	core.skillModService.deductSkillMod(actor, 'display_only_evasion', skillMod.getBase() / 3)
	core.skillModService.deductSkillMod(actor, 'display_only_critical', skillMod.getBase() / 3)
	core.skillModService.deductSkillMod(actor, 'display_only_strikethrough', skillMod.getBase() / 2)
	return
	