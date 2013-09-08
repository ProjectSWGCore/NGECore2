import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.addSkill('expertise_sp_expose_shadows_2')

	actor.addSkillMod('expertise_sp_reveal_shadows_distance_increase', 5)
	actor.addSkillMod('expertise_sp_reveal_shadows_detect_chance_increase', 3)
	actor.addSkillMod('expertise_cooldown_line_sp_reveal_shadows', 10)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.removeSkill('expertise_sp_expose_shadows_2')

	actor.removeSkillMod('expertise_sp_reveal_shadows_distance_increase', 5)
	actor.removeSkillMod('expertise_sp_reveal_shadows_detect_chance_increase', 3)
	actor.removeSkillMod('expertise_cooldown_line_sp_reveal_shadows', 10)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
