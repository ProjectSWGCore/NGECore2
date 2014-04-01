import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)	
	actor.setMaxHealth(actor.getMaxHealth() + int((float(actor.getMaxHealth()) * (float(base) / float(100)))))
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	actor.setMaxHealth(actor.getMaxHealth() - int((actor.getMaxHealth() * (float(base) / float(100)))))
	return
	