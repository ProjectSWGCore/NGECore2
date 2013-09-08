import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'commando_1a':
		return

	actor.addSkill('expertise_co_base_of_operations_1')

	actor.addSkillMod('expertise_co_pos_secured_line_armor', 1000)
	actor.addSkillMod('expertise_co_pos_secured_line_boo_critical', 5)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'commando_1a':
		return

	actor.removeSkill('expertise_co_base_of_operations_1')

	actor.removeSkillMod('expertise_co_pos_secured_line_armor', 1000)
	actor.removeSkillMod('expertise_co_pos_secured_line_boo_critical', 5)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
