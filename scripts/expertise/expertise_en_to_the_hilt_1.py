import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'entertainer_1a':
		return

	actor.addSkill('expertise_en_to_the_hilt_1')

	actor.addSkillMod('expertise_buff_duration_line_en_debuff_thrill', 2)
	actor.addSkillMod('expertise_en_debuff_thrill_increase', 2)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'entertainer_1a':
		return

	actor.removeSkill('expertise_en_to_the_hilt_1')

	actor.removeSkillMod('expertise_buff_duration_line_en_debuff_thrill', 2)
	actor.removeSkillMod('expertise_en_debuff_thrill_increase', 2)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
