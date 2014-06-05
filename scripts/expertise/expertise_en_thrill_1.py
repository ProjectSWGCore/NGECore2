import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 10:
		actor.addAbility("en_thrill")
	if actor.getLevel() >= 34:
		actor.addAbility("en_thrill_1")
	if actor.getLevel() >= 62:
		actor.addAbility("en_thrill_2")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("en_thrill")
	actor.removeAbility("en_thrill_1")
	actor.removeAbility("en_thrill_2")
	return
