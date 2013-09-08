import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.addSkill('expertise_sp_protective_armor_1')

	actor.addSkillMod('expertise_innate_protection_kinetic', 300)
	actor.addSkillMod('expertise_innate_protection_energy', 300)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.removeSkill('expertise_sp_protective_armor_1')

	actor.removeSkillMod('expertise_innate_protection_kinetic', 300)
	actor.removeSkillMod('expertise_innate_protection_energy', 300)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
