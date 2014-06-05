import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("co_remote_detonator_1")
	if actor.getLevel() >= 34:
		actor.addAbility("co_remote_detonator_2")
	if actor.getLevel() >= 48:
		actor.addAbility("co_remote_detonator_3")
	if actor.getLevel() >= 62:
		actor.addAbility("co_remote_detonator_4")
	if actor.getLevel() >= 76:
		actor.addAbility("co_remote_detonator_5")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("co_remote_detonator_1")
	actor.removeAbility("co_remote_detonator_2")
	actor.removeAbility("co_remote_detonator_3")
	actor.removeAbility("co_remote_detonator_4")
	actor.removeAbility("co_remote_detonator_5")
	return
