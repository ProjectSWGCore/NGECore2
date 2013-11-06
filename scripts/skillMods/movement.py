import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	actor.getSkillMod(name).setModifier(10)
	modifier = actor.getSkillMod(name).getModifier()
	actor.setSpeedMultiplierBase(actor.getSpeedMultiplierBase() + float(float(base) / float(modifier)))
	return

def deduct(core, actor, name, base):
	modifier = actor.getSkillMod(name).getModifier()
	actor.setSpeedMultiplierBase(actor.getSpeedMultiplierBase() - float(float(base) / float(modifier)))
	actor.deductSkillMod(name, base)
	return