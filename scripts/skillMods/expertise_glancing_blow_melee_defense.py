import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	return
	