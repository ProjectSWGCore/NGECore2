import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("me_bacta_grenade_1")
	if actor.getLevel() >= 34:
		actor.addAbility("me_bacta_grenade_2")
	if actor.getLevel() >= 48:
		actor.addAbility("me_bacta_grenade_3")
	if actor.getLevel() >= 62:
		actor.addAbility("me_bacta_grenade_4")
	if actor.getLevel() >= 76:
		actor.addAbility("me_bacta_grenade_5")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("me_bacta_grenade_1")
	actor.removeAbility("me_bacta_grenade_2")
	actor.removeAbility("me_bacta_grenade_3")
	actor.removeAbility("me_bacta_grenade_4")
	actor.removeAbility("me_bacta_grenade_5")
	return
