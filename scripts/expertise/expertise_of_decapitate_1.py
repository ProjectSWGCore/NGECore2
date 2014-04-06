import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("of_decapitate_1")
	if actor.getLevel() >= 34:
		actor.addAbility("of_decapitate_2")
	if actor.getLevel() >= 48:
		actor.addAbility("of_decapitate_3")
	if actor.getLevel() >= 62:
		actor.addAbility("of_decapitate_4")
	if actor.getLevel() >= 76:
		actor.addAbility("of_decapitate_5")
	if actor.getLevel() >= 90:
		actor.addAbility("of_decapitate_6")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("of_decapitate_1")
	actor.removeAbility("of_decapitate_2")
	actor.removeAbility("of_decapitate_3")
	actor.removeAbility("of_decapitate_4")
	actor.removeAbility("of_decapitate_5")
	actor.removeAbility("of_decapitate_6")
	return
