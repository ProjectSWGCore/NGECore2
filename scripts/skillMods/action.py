import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	actor.setMaxAction(actor.getMaxAction() + base)
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	actor.setMaxAction(actor.getMaxAction() - base)
	return
	