import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'force_sensitive_1a':
		return

	actor.addSkill('expertise_fs_path_saber_shackle_3')

	actor.addSkillMod('expertise_stance_saber_throw_snare_chance', 25)
	actor.addSkillMod('expertise_stance_saber_throw_root_chance', 2)
	actor.addSkillMod('expertise_damage_line_fs_dm', 1)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'force_sensitive_1a':
		return

	actor.removeSkill('expertise_fs_path_saber_shackle_3')

	actor.removeSkillMod('expertise_stance_saber_throw_snare_chance', 25)
	actor.removeSkillMod('expertise_stance_saber_throw_root_chance', 2)
	actor.removeSkillMod('expertise_damage_line_fs_dm', 1)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
