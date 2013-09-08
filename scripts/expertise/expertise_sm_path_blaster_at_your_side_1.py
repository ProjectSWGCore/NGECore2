import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'smuggler_1a':
		return

	actor.addSkill('expertise_sm_path_blaster_at_your_side_1')

	actor.addSkillMod('level_add_to_damage', 100)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'smuggler_1a':
		return

	actor.removeSkill('expertise_sm_path_blaster_at_your_side_1')

	actor.removeSkillMod('level_add_to_damage', 100)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
