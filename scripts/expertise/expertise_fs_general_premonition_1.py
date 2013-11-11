import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'force_sensitive_1a':
		return

	actor.addSkill('expertise_fs_general_premonition_1')

	core.skillModService.addSkillMod(actor, 'expertise_evasion_chance', 2)
	core.skillModService.addSkillMod(actor, 'expertise_evasion_value', 8)
	
	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'force_sensitive_1a':
		return

	actor.removeSkill('expertise_fs_general_premonition_1')

	core.skillModService.deductSkillMod(actor, 'expertise_evasion_chance', 2)
	core.skillModService.deductSkillMod(actor, 'expertise_evasion_value', 8)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
