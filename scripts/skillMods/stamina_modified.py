import sys

def add(core, actor, skillMod, divisor):
	actor.setMaxHealth(actor.getMaxHealth() + skillMod.getBase() * 2)
	actor.setMaxAction(actor.getMaxAction() + skillMod.getBase() * 8)
	return

def deduct(core, actor, skillMod, divisor):
	actor.setMaxHealth(actor.getMaxHealth() - skillMod.getBase() * 2)
	actor.setMaxAction(actor.getMaxAction() - skillMod.getBase() * 8)
	return
	