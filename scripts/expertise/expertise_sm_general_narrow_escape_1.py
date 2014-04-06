import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 34:
		actor.addAbility("sm_narrow_escape_1")
	if actor.getLevel() >= 50:
		actor.addAbility("sm_narrow_escape_2")
	if actor.getLevel() >= 66:
		actor.addAbility("sm_narrow_escape_3")
	if actor.getLevel() >= 82:
		actor.addAbility("sm_narrow_escape_4")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("sm_narrow_escape_1")
	actor.removeAbility("sm_narrow_escape_2")
	actor.removeAbility("sm_narrow_escape_3")
	actor.removeAbility("sm_narrow_escape_4")
	return
