import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'force_sensitive_1a':
		return

	actor.addSkill('expertise_fs_path_improved_saber_reflect_1')

	actor.addSkillMod('expertise_damage_line_fs_saber_reflect', 50)
	actor.addSkillMod('expertise_force_alacrity', 25)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'force_sensitive_1a':
		return

	actor.removeSkill('expertise_fs_path_improved_saber_reflect_1')

	actor.removeSkillMod('expertise_damage_line_fs_saber_reflect', 50)
	actor.removeSkillMod('expertise_force_alacrity', 25)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
