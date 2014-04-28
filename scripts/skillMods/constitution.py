import sys

def add(core, actor, skillMod, value):
	actor.setMaxHealth(actor.getMaxHealth() + skillMod.getBase() * 8)
	actor.setMaxAction(actor.getMaxAction() + skillMod.getBase() * 2)
	return

def deduct(core, actor, skillMod, value):
	actor.setMaxHealth(actor.getMaxHealth() - skillMod.getBase() * 8)
	actor.setMaxAction(actor.getMaxAction() - skillMod.getBase() * 2)
	return
	