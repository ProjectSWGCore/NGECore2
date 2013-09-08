import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.addSkill('expertise_sp_shadows_embrace_4')

	actor.addSkillMod('expertise_buff_duration_line_sp_burst_shadows', 2)
	actor.addSkillMod('expertise_cooldown_line_sp_burst_shadows', 30)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.removeSkill('expertise_sp_shadows_embrace_4')

	actor.removeSkillMod('expertise_buff_duration_line_sp_burst_shadows', 2)
	actor.removeSkillMod('expertise_cooldown_line_sp_burst_shadows', 30)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
