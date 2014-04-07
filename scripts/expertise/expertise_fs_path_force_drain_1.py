import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("fs_drain_1")
	if actor.getLevel() >= 34:
		actor.addAbility("fs_drain_2")
	if actor.getLevel() >= 48:
		actor.addAbility("fs_drain_3")
	if actor.getLevel() >= 62:
		actor.addAbility("fs_drain_4")
	if actor.getLevel() >= 76:
		actor.addAbility("fs_drain_5")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("fs_drain_1")
	actor.removeAbility("fs_drain_2")
	actor.removeAbility("fs_drain_3")
	actor.removeAbility("fs_drain_4")
	actor.removeAbility("fs_drain_5")
	return
