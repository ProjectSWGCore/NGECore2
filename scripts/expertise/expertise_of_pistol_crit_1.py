import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'officer_1a':
		return

	actor.addSkill('expertise_of_pistol_crit_1')

	actor.addSkillMod('expertise_undiminished_critical_pistol', 5)
	actor.addSkillMod('expertise_undiminished_critical_carbine', 5)
	actor.addSkillMod('expertise_range_bonus_pistol', 4)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'officer_1a':
		return

	actor.removeSkill('expertise_of_pistol_crit_1')

	actor.removeSkillMod('expertise_undiminished_critical_pistol', 5)
	actor.removeSkillMod('expertise_undiminished_critical_carbine', 5)
	actor.removeSkillMod('expertise_range_bonus_pistol', 4)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
