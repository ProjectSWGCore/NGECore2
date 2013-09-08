import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'smuggler_1a':
		return

	actor.addSkill('expertise_sm_path_quick_fix_4')

	actor.addSkillMod('expertise_healing_line_sm_heal', 10)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'smuggler_1a':
		return

	actor.removeSkill('expertise_sm_path_quick_fix_4')

	actor.removeSkillMod('expertise_healing_line_sm_heal', 10)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
