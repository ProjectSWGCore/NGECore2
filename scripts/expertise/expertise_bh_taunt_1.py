import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("bh_taunt_1")
	if actor.getLevel() >= 34:
		actor.addAbility("bh_taunt_2")
	if actor.getLevel() >= 48:
		actor.addAbility("bh_taunt_3")
	if actor.getLevel() >= 62:
		actor.addAbility("bh_taunt_4")
	if actor.getLevel() >= 76:
		actor.addAbility("bh_taunt_5")
	if actor.getLevel() >= 90:
		actor.addAbility("bh_taunt_6")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("bh_taunt_1")
	actor.removeAbility("bh_taunt_2")
	actor.removeAbility("bh_taunt_3")
	actor.removeAbility("bh_taunt_4")
	actor.removeAbility("bh_taunt_5")
	actor.removeAbility("bh_taunt_6")
	return
