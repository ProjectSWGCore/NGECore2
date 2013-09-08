import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'commando_1a':
		return

	actor.addSkill('expertise_co_burst_fire_2')

	actor.addSkillMod('expertise_co_pos_secured_line_burst_fire_proc', 10)
	actor.addSkillMod('expertise_co_pos_secured_line_burst_fire_devastation_bonus', 5)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'commando_1a':
		return

	actor.removeSkill('expertise_co_burst_fire_2')

	actor.removeSkillMod('expertise_co_pos_secured_line_burst_fire_proc', 10)
	actor.removeSkillMod('expertise_co_pos_secured_line_burst_fire_devastation_bonus', 5)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
