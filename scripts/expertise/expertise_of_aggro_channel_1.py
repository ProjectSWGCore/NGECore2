import sys

def addAbilities(core, actor, player):
	actor.addAbility("of_aggro_channel")
	actor.addAbility("expertise_of_aggro_channel_1")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("of_aggro_channel")
	actor.removeAbility("expertise_of_aggro_channel_1")
	return
