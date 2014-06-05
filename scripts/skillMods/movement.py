import sys

def add(core, actor, skillMod, divisor):
	actor.setSpeedMultiplierMod(actor.getSpeedMultiplierMod() + (float(skillMod.getBase()) / float(10)))
	return

def deduct(core, actor, skillMod, divisor):
	actor.setSpeedMultiplierMod(actor.getSpeedMultiplierMod() - (float(skillMod.getBase()) / float(10)))
	return
	