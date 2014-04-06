import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 4:
		actor.addAbility("of_deb_def_1")
	if actor.getLevel() >= 12:
		actor.addAbility("of_deb_def_2")
	if actor.getLevel() >= 20:
		actor.addAbility("of_deb_def_3")
	if actor.getLevel() >= 30:
		actor.addAbility("of_deb_def_4")
	if actor.getLevel() >= 38:
		actor.addAbility("of_deb_def_5")
	if actor.getLevel() >= 52:
		actor.addAbility("of_deb_def_6")
	if actor.getLevel() >= 62:
		actor.addAbility("of_deb_def_7")
	if actor.getLevel() >= 72:
		actor.addAbility("of_deb_def_8")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("of_deb_def_1")
	actor.removeAbility("of_deb_def_2")
	actor.removeAbility("of_deb_def_3")
	actor.removeAbility("of_deb_def_4")
	actor.removeAbility("of_deb_def_5")
	actor.removeAbility("of_deb_def_6")
	actor.removeAbility("of_deb_def_7")
	actor.removeAbility("of_deb_def_8")
	return
