import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("bh_sniper_1")
	if actor.getLevel() >= 34:
		actor.addAbility("bh_sniper_2")
	if actor.getLevel() >= 48:
		actor.addAbility("bh_sniper_3")
	if actor.getLevel() >= 62:
		actor.addAbility("bh_sniper_4")
	if actor.getLevel() >= 76:
		actor.addAbility("bh_sniper_5")
	if actor.getLevel() >= 90:
		actor.addAbility("bh_sniper_6")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("bh_sniper_1")
	actor.removeAbility("bh_sniper_2")
	actor.removeAbility("bh_sniper_3")
	actor.removeAbility("bh_sniper_4")
	actor.removeAbility("bh_sniper_5")
	actor.removeAbility("bh_sniper_6")
	return
