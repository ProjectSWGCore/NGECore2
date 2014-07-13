import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 10:
		actor.addAbility("en_heal_1")
	if actor.getLevel() >= 34:
		actor.addAbility("en_heal_2")
	if actor.getLevel() >= 58:
		actor.addAbility("en_heal_3")
	if actor.getLevel() >= 82:
		actor.addAbility("en_heal_4")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("en_heal_1")
	actor.removeAbility("en_heal_2")
	actor.removeAbility("en_heal_3")
	actor.removeAbility("en_heal_4")
	return
