import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	actor.setSpeedMultiplierBase(actor.getSpeedMultiplierBase() + base / 100)
	return

def deduct(core, actor, name, base):
	actor.setSpeedMultiplierBase(actor.getSpeedMultiplierBase() - base / 100)
	actor.deductSkillMod(name, base)
	return