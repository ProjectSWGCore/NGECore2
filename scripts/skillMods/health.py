import sys

def add(core, actor, skillMod, divisor):
	actor.setMaxHealth(actor.getMaxHealth() + skillMod.getBase())
	return

def deduct(core, actor, skillMod, divisor):
	actor.setMaxHealth(actor.getMaxHealth() - skillMod.getBase())
	return
	