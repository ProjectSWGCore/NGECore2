import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.addSkill('expertise_sp_equilibrium')

	actor.addSkillMod('freeshot_case_miss', 1)
	actor.addSkillMod('freeshot_case_dodge', 1)
	actor.addSkillMod('freeshot_case_parry', 1)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.removeSkill('expertise_sp_equilibrium')

	actor.removeSkillMod('freeshot_case_miss', 1)
	actor.removeSkillMod('freeshot_case_dodge', 1)
	actor.removeSkillMod('freeshot_case_parry', 1)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
