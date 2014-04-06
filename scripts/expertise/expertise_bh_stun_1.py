import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("bh_stun_1")
	if actor.getLevel() >= 34:
		actor.addAbility("bh_stun_2")
	if actor.getLevel() >= 48:
		actor.addAbility("bh_stun_3")
	if actor.getLevel() >= 62:
		actor.addAbility("bh_stun_4")
	if actor.getLevel() >= 76:
		actor.addAbility("bh_stun_5")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("bh_stun_1")
	actor.removeAbility("bh_stun_2")
	actor.removeAbility("bh_stun_3")
	actor.removeAbility("bh_stun_4")
	actor.removeAbility("bh_stun_5")
	return
