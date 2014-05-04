import sys

def add(core, actor, skillMod, divisor):
	core.skillModService.addSkillMod(actor, 'display_only_block', skillMod.getBase() / 2)
	core.skillModService.addSkillMod(actor, 'display_only_parry', skillMod.getBase() / 2)
	core.skillModService.addSkillMod(actor, 'display_only_critical', skillMod.getBase())
	core.skillModService.addSkillMod(actor, 'display_only_strikethrough', skillMod.getBase() / 2)
	return

def deduct(core, actor, skillMod, divisor):
	core.skillModService.deductSkillMod(actor, 'display_only_block', skillMod.getBase() / 2)
	core.skillModService.deductSkillMod(actor, 'display_only_parry', skillMod.getBase() / 2)
	core.skillModService.deductSkillMod(actor, 'display_only_critical', skillMod.getBase())
	core.skillModService.deductSkillMod(actor, 'display_only_strikethrough', skillMod.getBase() / 2)
	return
	