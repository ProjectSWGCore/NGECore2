import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 4:
		actor.addAbility("fs_flurry_1")
	if actor.getLevel() >= 12:
		actor.addAbility("fs_flurry_2")
	if actor.getLevel() >= 20:
		actor.addAbility("fs_flurry_3")
	if actor.getLevel() >= 30:
		actor.addAbility("fs_flurry_4")
	if actor.getLevel() >= 38:
		actor.addAbility("fs_flurry_5")
	if actor.getLevel() >= 52:
		actor.addAbility("fs_flurry_6")
	if actor.getLevel() >= 62:
		actor.addAbility("fs_flurry_7")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("fs_flurry_1")
	actor.removeAbility("fs_flurry_2")
	actor.removeAbility("fs_flurry_3")
	actor.removeAbility("fs_flurry_4")
	actor.removeAbility("fs_flurry_5")
	actor.removeAbility("fs_flurry_6")
	actor.removeAbility("fs_flurry_7")

	return
