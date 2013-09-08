import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'bounty_hunter_1a':
		return

	actor.addSkill('expertise_bh_trap_duration_3')

	actor.addSkillMod('expertise_buff_duration_group_snare', 1)
	actor.addSkillMod('expertise_cooldown_line_dm_cc', 20)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'bounty_hunter_1a':
		return

	actor.removeSkill('expertise_bh_trap_duration_3')

	actor.removeSkillMod('expertise_buff_duration_group_snare', 1)
	actor.removeSkillMod('expertise_cooldown_line_dm_cc', 20)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
