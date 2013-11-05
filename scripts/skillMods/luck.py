import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	actor.addSkillMod('display_only_dodge', base / 3)
	actor.addSkillMod('display_only_evasion', base / 3)
	actor.addSkillMod('display_only_critical', base / 3)
	actor.addSkillMod('display_only_strikethrough', base / 2)
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	actor.deductSkillMod('display_only_dodge', base / 3)
	actor.deductSkillMod('display_only_evasion', base / 3)
	actor.deductSkillMod('display_only_critical', base / 3)
	actor.deductSkillMod('display_only_strikethrough', base / 2)
	return
	