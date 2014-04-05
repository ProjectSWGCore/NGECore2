import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 4:
		actor.addAbility("en_strike_0")
	if actor.getLevel() >= 12:
		actor.addAbility("en_strike_1")
	if actor.getLevel() >= 20:
		actor.addAbility("en_strike_2")
	if actor.getLevel() >= 30:
		actor.addAbility("en_strike_3")
	if actor.getLevel() >= 38:
		actor.addAbility("en_strike_4")
	if actor.getLevel() >= 52:
		actor.addAbility("en_strike_5")
	if actor.getLevel() >= 62:
		actor.addAbility("en_strike_6")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("en_strike_0")
	actor.removeAbility("en_strike_1")
	actor.removeAbility("en_strike_2")
	actor.removeAbility("en_strike_3")
	actor.removeAbility("en_strike_4")
	actor.removeAbility("en_strike_5")
	actor.removeAbility("en_strike_6")
	return
