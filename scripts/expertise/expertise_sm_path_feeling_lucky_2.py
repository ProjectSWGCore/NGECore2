import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'smuggler_1a':
		return

	actor.addSkill('expertise_sm_path_feeling_lucky_2')

	actor.addSkillMod('expertise_increase_smuggler_loot', 1)
	actor.addSkillMod('expertise_sm_feeling_lucky_chance_proc', 1)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'smuggler_1a':
		return

	actor.removeSkill('expertise_sm_path_feeling_lucky_2')

	actor.removeSkillMod('expertise_increase_smuggler_loot', 1)
	actor.removeSkillMod('expertise_sm_feeling_lucky_chance_proc', 1)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
