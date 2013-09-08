import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'force_sensitive_1a':
		return

	actor.addSkill('expertise_fs_path_wracking_energy_4')

	actor.addSkillMod('expertise_fs_dm_armor_bypass', 25)
	actor.addSkillMod('expertise_buff_duration_line_fs_ae_dm_cc', 2)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'force_sensitive_1a':
		return

	actor.removeSkill('expertise_fs_path_wracking_energy_4')

	actor.removeSkillMod('expertise_fs_dm_armor_bypass', 25)
	actor.removeSkillMod('expertise_buff_duration_line_fs_ae_dm_cc', 2)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
