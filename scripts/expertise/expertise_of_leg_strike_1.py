import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 4:
		actor.addAbility("of_leg_strike_1")
	if actor.getLevel() >= 12:
		actor.addAbility("of_leg_strike_2")
	if actor.getLevel() >= 20:
		actor.addAbility("of_leg_strike_3")
	if actor.getLevel() >= 30:
		actor.addAbility("of_leg_strike_4")
	if actor.getLevel() >= 38:
		actor.addAbility("of_leg_strike_5")
	if actor.getLevel() >= 52:
		actor.addAbility("of_leg_strike_6")
	if actor.getLevel() >= 62:
		actor.addAbility("of_leg_strike_7")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("of_leg_strike_1")
	actor.removeAbility("of_leg_strike_2")
	actor.removeAbility("of_leg_strike_3")
	actor.removeAbility("of_leg_strike_4")
	actor.removeAbility("of_leg_strike_5")
	actor.removeAbility("of_leg_strike_6")
	actor.removeAbility("of_leg_strike_7")
	return
