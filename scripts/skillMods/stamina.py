import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	actor.setMaxHealth(actor.getMaxHealth() + base * 2)
	actor.setMaxAction(actor.getMaxAction() + base * 8)
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	actor.setMaxHealth(actor.getMaxHealth() - base * 2)
	actor.setMaxAction(actor.getMaxAction() - base * 8)
	return
	
