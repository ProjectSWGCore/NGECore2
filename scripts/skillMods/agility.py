import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	actor.addSkillMod('display_only_dodge', base)
	actor.addSkillMod('display_only_parry', base / 2)
	actor.addSkillMod('display_only_evasion', base)
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	actor.deductSkillMod('display_only_dodge', base)
	actor.deductSkillMod('display_only_parry', base / 2)
	actor.deductSkillMod('display_only_evasion', base)
	return
	