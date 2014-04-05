import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("sp_dot_0")
	if actor.getLevel() >= 34:
		actor.addAbility("sp_dot_1")
	if actor.getLevel() >= 48:
		actor.addAbility("sp_dot_2")
	if actor.getLevel() >= 62:
		actor.addAbility("sp_dot_3")
	if actor.getLevel() >= 76:
		actor.addAbility("sp_dot_4")
	if actor.getLevel() >= 90:
		actor.addAbility("sp_dot_5")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("sp_dot_0")
	actor.removeAbility("sp_dot_1")
	actor.removeAbility("sp_dot_2")
	actor.removeAbility("sp_dot_3")
	actor.removeAbility("sp_dot_4")
	actor.removeAbility("sp_dot_5")
	return
