import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'bounty_hunter_1a':
		return

	actor.addSkill('expertise_bh_trap_rng_2')

	actor.addSkillMod('expertise_area_size_line_trap', 1)
	actor.addSkillMod('expertise_area_size_line_diretrap', 1)
	actor.addSkillMod('expertise_action_line_trap', 10)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'bounty_hunter_1a':
		return

	actor.removeSkill('expertise_bh_trap_rng_2')

	actor.removeSkillMod('expertise_area_size_line_trap', 1)
	actor.removeSkillMod('expertise_area_size_line_diretrap', 1)
	actor.removeSkillMod('expertise_action_line_trap', 10)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
