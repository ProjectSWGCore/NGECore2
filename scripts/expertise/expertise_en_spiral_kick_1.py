import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("en_spiral_kick_0")
	if actor.getLevel() >= 34:
		actor.addAbility("en_spiral_kick_1")
	if actor.getLevel() >= 48:
		actor.addAbility("en_spiral_kick_2")
	if actor.getLevel() >= 62:
		actor.addAbility("en_spiral_kick_3")
	if actor.getLevel() >= 76:
		actor.addAbility("en_spiral_kick_4")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("en_spiral_kick_0")
	actor.removeAbility("en_spiral_kick_1")
	actor.removeAbility("en_spiral_kick_2")
	actor.removeAbility("en_spiral_kick_3")
	actor.removeAbility("en_spiral_kick_4")
	return
