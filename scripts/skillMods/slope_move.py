import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	actor.setSpeedMultiplierBase(base / 1.5)
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	actor.setSpeedMultiplierBase(1)
	return
	