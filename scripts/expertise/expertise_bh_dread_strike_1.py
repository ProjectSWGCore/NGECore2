import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("bh_dread_strike_1")
	if actor.getLevel() >= 34:
		actor.addAbility("bh_dread_strike_2")
	if actor.getLevel() >= 48:
		actor.addAbility("bh_dread_strike_3")
	if actor.getLevel() >= 62:
		actor.addAbility("bh_dread_strike_4")
	if actor.getLevel() >= 76:
		actor.addAbility("bh_dread_strike_5")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("bh_dread_strike_1")
	actor.removeAbility("bh_dread_strike_2")
	actor.removeAbility("bh_dread_strike_3")
	actor.removeAbility("bh_dread_strike_4")
	actor.removeAbility("bh_dread_strike_5")
	return
