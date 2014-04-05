import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("me_bacta_bomb_1")
	if actor.getLevel() >= 34:
		actor.addAbility("me_bacta_bomb_2")
	if actor.getLevel() >= 48:
		actor.addAbility("me_bacta_bomb_3")
	if actor.getLevel() >= 62:
		actor.addAbility("me_bacta_bomb_4")
	if actor.getLevel() >= 76:
		actor.addAbility("me_bacta_bomb_5")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("me_bacta_bomb_1")
	actor.removeAbility("me_bacta_bomb_2")
	actor.removeAbility("me_bacta_bomb_3")
	actor.removeAbility("me_bacta_bomb_4")
	actor.removeAbility("me_bacta_bomb_5")
	return
