import sys

def add(core, actor, skillMod, divisor):
	# setSpeedMultiplier(1.1)
	# print(getSpeedMultiplier())
	# > 1.10000002384
	if skillMod.getBase() != 0:
		actor.setSpeedMultiplierMod(round(actor.getSpeedMultiplierMod(), 2) + skillMod.getBase() / float(100))
	return

def deduct(core, actor, skillMod, divisor):
	if skillMod.getBase() != 0:
		actor.setSpeedMultiplierMod(round(actor.getSpeedMultiplierMod(), 2) - skillMod.getBase() / float(100))
	return