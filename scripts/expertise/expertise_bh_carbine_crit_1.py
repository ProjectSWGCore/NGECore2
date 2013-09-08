import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'bounty_hunter_1a':
		return

	actor.addSkill('expertise_bh_carbine_crit_1')

	actor.addSkillMod('expertise_range_bonus_carbine', 10)
	actor.addSkillMod('expertise_undiminished_critical_carbine', 10)
	actor.addSkillMod('expertise_undiminished_critical_pistol', 10)
	actor.addSkillMod('expertise_range_bonus_pistol', 5)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'bounty_hunter_1a':
		return

	actor.removeSkill('expertise_bh_carbine_crit_1')

	actor.removeSkillMod('expertise_range_bonus_carbine', 10)
	actor.removeSkillMod('expertise_undiminished_critical_carbine', 10)
	actor.removeSkillMod('expertise_undiminished_critical_pistol', 10)
	actor.removeSkillMod('expertise_range_bonus_pistol', 5)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
