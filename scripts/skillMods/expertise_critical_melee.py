import sys

def add(core, actor, name, base):
	actor.addSkillMod("expertise_critical_1h", base)
	actor.addSkillMod("expertise_critical_2h", base)
	actor.addSkillMod("expertise_critical_unarmed", base)
	actor.addSkillMod("expertise_critical_polearm", base)
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod("expertise_critical_1h", base)
	actor.deductSkillMod("expertise_critical_2h", base)
	actor.deductSkillMod("expertise_critical_unarmed", base)
	actor.deductSkillMod("expertise_critical_polearm", base)
	return
	