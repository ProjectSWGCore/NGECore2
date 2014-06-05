import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("en_sweeping_pirouette_0")
	if actor.getLevel() >= 38:
		actor.addAbility("en_sweeping_pirouette_1")
	if actor.getLevel() >= 50:
		actor.addAbility("en_sweeping_pirouette_2")
	if actor.getLevel() >= 62:
		actor.addAbility("en_sweeping_pirouette_3")
	if actor.getLevel() >= 74:
		actor.addAbility("en_sweeping_pirouette_4")
	if actor.getLevel() >= 86:
		actor.addAbility("en_sweeping_pirouette_5")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("en_sweeping_pirouette_0")
	return
