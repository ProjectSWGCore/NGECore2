import sys

def add((core, actor, skillMod, divisor):
	core.skillModService.addSkillMod('display_only_dodge', skillMod.getBase())
	core.skillModService.addSkillMod('display_only_parry', skillMod.getBase() / 2)
	core.skillModService.addSkillMod('display_only_evasion', skillMod.getBase())
	return

def deduct((core, actor, skillMod, divisor):
	core.skillModService.deductSkillMod('display_only_dodge', skillMod.getBase())
	core.skillModService.deductSkillMod('display_only_parry', skillMod.getBase() / 2)
	core.skillModService.deductSkillMod('display_only_evasion', skillMod.getBase())
	return
	