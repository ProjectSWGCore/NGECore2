import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 10:
		actor.addAbility("en_void_dance_1")
	if actor.getLevel() >= 34:
		actor.addAbility("en_void_dance_2")
	if actor.getLevel() >= 62:
		actor.addAbility("en_void_dance_3")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("en_void_dance_1")
	actor.removeAbility("en_void_dance_2")
	actor.removeAbility("en_void_dance_3")
	return
