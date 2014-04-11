import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("fs_maelstrom_1")
	if actor.getLevel() >= 34:
		actor.addAbility("fs_maelstrom_2")
	if actor.getLevel() >= 48:
		actor.addAbility("fs_maelstrom_3")
	if actor.getLevel() >= 62:
		actor.addAbility("fs_maelstrom_4")
	if actor.getLevel() >= 76:
		actor.addAbility("fs_maelstrom_5")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("fs_maelstrom_1")
	actor.removeAbility("fs_maelstrom_2")
	actor.removeAbility("fs_maelstrom_3")
	actor.removeAbility("fs_maelstrom_4")
	actor.removeAbility("fs_maelstrom_5")
	return
