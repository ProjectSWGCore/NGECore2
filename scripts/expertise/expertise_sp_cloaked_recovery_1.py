import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 10:
		actor.addAbility("sp_cloaked_recovery_0")
	if actor.getLevel() >= 28:
		actor.addAbility("sp_cloaked_recovery_1")
	if actor.getLevel() >= 54:
		actor.addAbility("sp_cloaked_recovery_2")
	if actor.getLevel() >= 70:
		actor.addAbility("sp_cloaked_recovery_3")
	if actor.getLevel() >= 86:
		actor.addAbility("sp_cloaked_recovery_4")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("sp_cloaked_recovery_0")
	actor.removeAbility("sp_cloaked_recovery_1")
	actor.removeAbility("sp_cloaked_recovery_2")
	actor.removeAbility("sp_cloaked_recovery_3")
	actor.removeAbility("sp_cloaked_recovery_4")
	return
