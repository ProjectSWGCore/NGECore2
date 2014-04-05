import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 34:
		actor.addAbility("sp_improved_cc_dot_1")
	if actor.getLevel() >= 50:
		actor.addAbility("sp_improved_cc_dot_2")
	if actor.getLevel() >= 66:
		actor.addAbility("sp_improved_cc_dot_3")
	if actor.getLevel() >= 82:
		actor.addAbility("sp_improved_cc_dot_4")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("sp_improved_cc_dot_1")
	actor.removeAbility("sp_improved_cc_dot_2")
	actor.removeAbility("sp_improved_cc_dot_3")
	actor.removeAbility("sp_improved_cc_dot_4")
	return
