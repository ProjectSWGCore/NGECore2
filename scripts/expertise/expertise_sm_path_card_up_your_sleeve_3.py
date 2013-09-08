import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'smuggler_1a':
		return

	actor.addSkill('expertise_sm_path_card_up_your_sleeve_3')

	actor.addSkillMod('expertise_double_hit_chance', 10)
	actor.addSkillMod('expertise_sm_card_ranged_proc', 25)
	actor.addSkillMod('expertise_sm_card_melee_proc', 25)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'smuggler_1a':
		return

	actor.removeSkill('expertise_sm_path_card_up_your_sleeve_3')

	actor.removeSkillMod('expertise_double_hit_chance', 10)
	actor.removeSkillMod('expertise_sm_card_ranged_proc', 25)
	actor.removeSkillMod('expertise_sm_card_melee_proc', 25)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
