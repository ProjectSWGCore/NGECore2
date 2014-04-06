import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 10:
		actor.addAbility("of_reinforcements_1")
	if actor.getLevel() >= 34:
		actor.addAbility("of_reinforcements_2")
	if actor.getLevel() >= 48:
		actor.addAbility("of_reinforcements_3")
	if actor.getLevel() >= 62:
		actor.addAbility("of_reinforcements_4")
	if actor.getLevel() >= 76:
		actor.addAbility("of_reinforcements_5")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("of_reinforcements_1")
	actor.removeAbility("of_reinforcements_2")
	actor.removeAbility("of_reinforcements_3")
	actor.removeAbility("of_reinforcements_4")
	actor.removeAbility("of_reinforcements_5")
	return
