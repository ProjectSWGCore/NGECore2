import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'all_1a':
		return

	actor.addSkill('expertise_bm_incubation_base_1')

	actor.addSkillMod('expertise_bm_base_mod', 100)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'all_1a':
		return

	actor.removeSkill('expertise_bm_incubation_base_1')

	actor.removeSkillMod('expertise_bm_base_mod', 100)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):

	actor.addAbility('bm_collect_dna')

	return

def removeAbilities(core, actor, player):

	actor.removeAbility('bm_collect_dna')

	return
