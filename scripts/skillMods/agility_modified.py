import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	actor.addSkillMod('display_only_dodge', 10)
	actor.addSkillMod('display_only_parry', 5)
	actor.addSkillMod('display_only_evasion', 10)
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	actor.deductSkillMod('display_only_dodge', 10)
	actor.deductSkillMod('display_only_parry', 5)
	actor.deductSkillMod('display_only_evasion', 10)
	return
	