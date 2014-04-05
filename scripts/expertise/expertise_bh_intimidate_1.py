import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("bh_intimidate_1")
	if actor.getLevel() >= 34:
		actor.addAbility("bh_intimidate_2")
	if actor.getLevel() >= 48:
		actor.addAbility("bh_intimidate_3")
	if actor.getLevel() >= 62:
		actor.addAbility("bh_intimidate_4")
	if actor.getLevel() >= 76:
		actor.addAbility("bh_intimidate_5")
	if actor.getLevel() >= 90:
		actor.addAbility("bh_intimidate_6")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("bh_intimidate_1")
	actor.removeAbility("bh_intimidate_2")
	actor.removeAbility("bh_intimidate_3")
	actor.removeAbility("bh_intimidate_4")
	actor.removeAbility("bh_intimidate_5")
	actor.removeAbility("bh_intimidate_6")
	return
