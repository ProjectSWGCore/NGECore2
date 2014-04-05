import sys

def add(core, actor, name, base):
	#actor.addSkillMod(name, base)
	actor.setSpeedMultiplierMod(actor.getSpeedMultiplierMod() + (float(base) / float(10)))
	return

def deduct(core, actor, name, base):
	#actor.deductSkillMod(name, base)
	actor.setSpeedMultiplierMod(actor.getSpeedMultiplierMod() - (float(base) / float(10)))
	return