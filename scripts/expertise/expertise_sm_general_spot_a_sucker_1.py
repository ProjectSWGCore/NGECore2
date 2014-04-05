import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 34:
		actor.addAbility("sm_spot_a_sucker_1")
	if actor.getLevel() >= 50:
		actor.addAbility("sm_spot_a_sucker_2")
	if actor.getLevel() >= 66:
		actor.addAbility("sm_spot_a_sucker_3")
	if actor.getLevel() >= 82:
		actor.addAbility("sm_spot_a_sucker_4")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("sm_spot_a_sucker_1")
	actor.removeAbility("sm_spot_a_sucker_2")
	actor.removeAbility("sm_spot_a_sucker_3")
	actor.removeAbility("sm_spot_a_sucker_4")
	return
