import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'trader_eng_1a':
		return

	actor.addSkill('expertise_cybernetic_dexterity_1')

	actor.addSkillMod('cybernetic_assembly', 5)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'trader_eng_1a':
		return

	actor.removeSkill('expertise_cybernetic_dexterity_1')

	actor.removeSkillMod('cybernetic_assembly', 5)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
