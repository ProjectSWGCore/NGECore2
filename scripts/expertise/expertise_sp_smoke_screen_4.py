import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.addSkill('expertise_sp_smoke_screen_4')

	actor.addSkillMod('expertise_movement_buff_invis_sp_buff_invis_1', -10)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.removeSkill('expertise_sp_smoke_screen_4')

	actor.removeSkillMod('expertise_movement_buff_invis_sp_buff_invis_1', -10)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
