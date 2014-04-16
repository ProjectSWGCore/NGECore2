import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 10:
		actor.addAbility("en_strike_0")
	if actor.getLevel() >= 22:
		actor.addAbility("en_strike_1")
	if actor.getLevel() >= 34:
		actor.addAbility("en_strike_2")
	if actor.getLevel() >= 46:
		actor.addAbility("en_strike_3")
	if actor.getLevel() >= 58:
		actor.addAbility("en_strike_4")
	if actor.getLevel() >= 70:
		actor.addAbility("en_strike_5")
	if actor.getLevel() >= 82:
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
