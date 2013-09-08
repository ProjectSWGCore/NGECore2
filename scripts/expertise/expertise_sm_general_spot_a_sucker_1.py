import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'smuggler_1a':
		return

	actor.addSkill('expertise_sm_general_spot_a_sucker_1')


	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'smuggler_1a':
		return

	actor.removeSkill('expertise_sm_general_spot_a_sucker_1')


	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):

	actor.addAbility('sm_spot_a_sucker_1')

	return

def removeAbilities(core, actor, player):

	actor.removeAbility('sm_spot_a_sucker_1')

	return
