import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	actor.setSpeedMultiplierMod(actor.getSpeedMultiplierMod() - (float(base) / float(10)))
	actor.setSpeedMultiplierBase(actor.getSpeedMultiplierBase() + base / 100)
	return

def deduct(core, actor, name, base):
	actor.setSpeedMultiplierBase(actor.getSpeedMultiplierBase() - base / 100)
	actor.setSpeedMultiplierMod(actor.getSpeedMultiplierMod() - (float(base) / float(10)))
	actor.deductSkillMod(name, base)
	return