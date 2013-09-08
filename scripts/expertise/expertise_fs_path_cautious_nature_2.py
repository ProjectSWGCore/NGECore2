import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'force_sensitive_1a':
		return

	actor.addSkill('expertise_fs_path_cautious_nature_2')

	actor.addSkillMod('expertise_stance_constitution', 10)
	actor.addSkillMod('expertise_stance_parry', 1)
	actor.addSkillMod('expertise_stance_evasion', 1)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'force_sensitive_1a':
		return

	actor.removeSkill('expertise_fs_path_cautious_nature_2')

	actor.removeSkillMod('expertise_stance_constitution', 10)
	actor.removeSkillMod('expertise_stance_parry', 1)
	actor.removeSkillMod('expertise_stance_evasion', 1)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
