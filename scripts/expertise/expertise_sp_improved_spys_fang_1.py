import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 10:
		actor.addAbility("sp_dot_0")
	if actor.getLevel() >= 28:
		actor.addAbility("sp_dot_1")
	if actor.getLevel() >= 46:
		actor.addAbility("sp_dot_2")
	if actor.getLevel() >= 58:
		actor.addAbility("sp_dot_3")
	if actor.getLevel() >= 72:
		actor.addAbility("sp_dot_4")
	if actor.getLevel() >= 88:
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
