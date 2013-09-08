import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'force_sensitive_1a':
		return

	actor.addSkill('expertise_fs_path_improved_force_choke_2')

	actor.addSkillMod('expertise_damage_line_fs_dm_cc', 10)
	actor.addSkillMod('expertise_dot_damage_line_fs_dm_cc', 10)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'force_sensitive_1a':
		return

	actor.removeSkill('expertise_fs_path_improved_force_choke_2')

	actor.removeSkillMod('expertise_damage_line_fs_dm_cc', 10)
	actor.removeSkillMod('expertise_dot_damage_line_fs_dm_cc', 10)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
