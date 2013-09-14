import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'bounty_hunter_1a':
		return

	actor.addSkill('expertise_bh_amb_dam_cool')

	actor.addSkillMod('expertise_dm_crit_advanced', 1)
	actor.addSkillMod('expertise_critical_line_dm_crit', 10)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'bounty_hunter_1a':
		return

	actor.removeSkill('expertise_bh_amb_dam_cool')

	actor.removeSkillMod('expertise_dm_crit_advanced', 1)
	actor.removeSkillMod('expertise_critical_line_dm_crit', 10)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
