import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 10:
		actor.addAbility("of_tactical_sup_1")
	if actor.getLevel() >= 34:
		actor.addAbility("of_tactical_sup_2")
	if actor.getLevel() >= 48:
		actor.addAbility("of_tactical_sup_3")
	if actor.getLevel() >= 62:
		actor.addAbility("of_tactical_sup_4")
	if actor.getLevel() >= 76:
		actor.addAbility("of_tactical_sup_5")
	if actor.getLevel() >= 90:
		actor.addAbility("of_tactical_sup_6")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("of_tactical_sup_1")
	actor.removeAbility("of_tactical_sup_2")
	actor.removeAbility("of_tactical_sup_3")
	actor.removeAbility("of_tactical_sup_4")
	actor.removeAbility("of_tactical_sup_5")
	actor.removeAbility("of_tactical_sup_6")
	return
