import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	actor.setMaxHealth(actor.getHealth() + base * 2)
	actor.setMaxAction(actor.getAction() + base * 8)
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	actor.setMaxHealth(actor.getHealth() - base * 2)
	actor.setMaxAction(actor.getAction() - base * 8)
	return
	