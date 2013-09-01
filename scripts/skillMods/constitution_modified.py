import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	actor.setMaxHealth(actor.getMaxHealth() + base * 8)
	actor.setMaxAction(actor.getMaxAction() + base * 2)
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	actor.setMaxHealth(actor.getMaxHealth() - base * 8)
	actor.setMaxAction(actor.getMaxAction() - base * 2)
	return
	
