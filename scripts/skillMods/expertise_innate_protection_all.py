import sys

def add(core, actor, name, base):
	actor.addSkillMod('energy', base)
	actor.addSkillMod('kinetic', base)
	actor.addSkillMod('acid', base)
	actor.addSkillMod('heat', base)
	actor.addSkillMod('cold', base)
	actor.addSkillMod('electricity', base)
	actor.addSkillMod(name, base)
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod('energy', base)
	actor.deductSkillMod('kinetic', base)
	actor.deductSkillMod('acid', base)
	actor.deductSkillMod('heat', base)
	actor.deductSkillMod('cold', base)
	actor.deductSkillMod('electricity', base)
	actor.deductSkillMod(name, base)
	return
	