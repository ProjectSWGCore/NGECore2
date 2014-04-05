import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("bh_armor_duelist_1")
	if actor.getLevel() >= 34:
		actor.addAbility("bh_armor_duelist_2")
	if actor.getLevel() >= 48:
		actor.addAbility("bh_armor_duelist_3")
	if actor.getLevel() >= 62:
		actor.addAbility("bh_armor_duelist_4")
	if actor.getLevel() >= 76:
		actor.addAbility("bh_armor_duelist_5")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("bh_armor_duelist_1")
	actor.removeAbility("bh_armor_duelist_2")
	actor.removeAbility("bh_armor_duelist_3")
	actor.removeAbility("bh_armor_duelist_4")
	actor.removeAbility("bh_armor_duelist_5")
	return
