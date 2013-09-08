import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'force_sensitive_1a':
		return

	actor.addSkill('expertise_fs_path_lethargy_3')

	actor.addSkillMod('expertise_buff_duration_line_fs_mind_trick', 2)
	actor.addSkillMod('expertise_action_damage_line_fs_mind_trick', 250)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'force_sensitive_1a':
		return

	actor.removeSkill('expertise_fs_path_lethargy_3')

	actor.removeSkillMod('expertise_buff_duration_line_fs_mind_trick', 2)
	actor.removeSkillMod('expertise_action_damage_line_fs_mind_trick', 250)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
