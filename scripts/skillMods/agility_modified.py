import sys

def add(core, actor, skillMod, value):
	core.skillModService.addSkillMod('display_only_dodge', base)
	core.skillModService.addSkillMod('display_only_parry', base / 2)
	core.skillModService.addSkillMod('display_only_evasion', base)
	return

def deduct(core, actor, skillMod, value):
	core.skillModService.deductSkillMod('display_only_dodge', base)
	core.skillModService.deductSkillMod('display_only_parry', base / 2)
	core.skillModService.deductSkillMod('display_only_evasion', base)
	return
	