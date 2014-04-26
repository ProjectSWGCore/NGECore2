import sys

def add(core, actor, skillMod, value):
	core.skillModService.addSkillMod('display_only_dodge', skillMod.getBase())
	core.skillModService.addSkillMod('display_only_parry', skillMod.getBase() / 2)
	core.skillModService.addSkillMod('display_only_evasion', skillMod.getBase())
	return

def deduct(core, actor, skillMod, value):
	core.skillModService.deductSkillMod('display_only_dodge', skillMod.getBase())
	core.skillModService.deductSkillMod('display_only_parry', skillMod.getBase() / 2)
	core.skillModService.deductSkillMod('display_only_evasion', skillMod.getBase())
	return
	