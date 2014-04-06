import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("of_vortex_1")
	if actor.getLevel() >= 34:
		actor.addAbility("of_vortex_2")
	if actor.getLevel() >= 48:
		actor.addAbility("of_vortex_3")
	if actor.getLevel() >= 62:
		actor.addAbility("of_vortex_4")
	if actor.getLevel() >= 76:
		actor.addAbility("of_vortex_5")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("of_vortex_1")
	actor.removeAbility("of_vortex_2")
	actor.removeAbility("of_vortex_3")
	actor.removeAbility("of_vortex_4")
	actor.removeAbility("of_vortex_5")
	return
