import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	actor.setMaxHealth(actor.getMaxHealth() + base)
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	actor.setMaxHealth(actor.getMaxHealth() - base)
	return
	