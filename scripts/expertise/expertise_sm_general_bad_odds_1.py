import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("sm_bad_odds_1")
	if actor.getLevel() >= 34:
		actor.addAbility("sm_bad_odds_2")
	if actor.getLevel() >= 48:
		actor.addAbility("sm_bad_odds_3")
	if actor.getLevel() >= 62:
		actor.addAbility("sm_bad_odds_4")
	if actor.getLevel() >= 76:
		actor.addAbility("sm_bad_odds_5")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("sm_bad_odds_1")
	actor.removeAbility("sm_bad_odds_2")
	actor.removeAbility("sm_bad_odds_3")
	actor.removeAbility("sm_bad_odds_4")
	actor.removeAbility("sm_bad_odds_5")
	return
