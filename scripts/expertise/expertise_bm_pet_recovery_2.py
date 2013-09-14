import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'all_1a':
		return

	actor.addSkill('expertise_bm_pet_recovery_2')

	actor.addSkillMod('expertise_bm_pet_revive_time', 5)
	actor.addSkillMod('expertise_bm_pet_recovery', 25)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'all_1a':
		return

	actor.removeSkill('expertise_bm_pet_recovery_2')

	actor.removeSkillMod('expertise_bm_pet_revive_time', 5)
	actor.removeSkillMod('expertise_bm_pet_recovery', 25)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
