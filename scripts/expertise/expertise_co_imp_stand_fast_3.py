import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'commando_1a':
		return

	actor.addSkill('expertise_co_imp_stand_fast_3')

	actor.addSkillMod('expertise_damage_decrease_percentage', 5)
	actor.addSkillMod('expertise_buff_duration_line_co_stand_fast', 2)
	actor.addSkillMod('expertise_cooldown_line_co_stand_fast', 120)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'commando_1a':
		return

	actor.removeSkill('expertise_co_imp_stand_fast_3')

	actor.removeSkillMod('expertise_damage_decrease_percentage', 5)
	actor.removeSkillMod('expertise_buff_duration_line_co_stand_fast', 2)
	actor.removeSkillMod('expertise_cooldown_line_co_stand_fast', 120)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
