import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'commando_1a':
		return

	actor.addSkill('expertise_co_youll_regret_that_4')

	actor.addSkillMod('kill_meter_co_youll_regret_that_reac', 0)
	actor.addSkillMod('expertise_youll_regret_that', 1000)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'commando_1a':
		return

	actor.removeSkill('expertise_co_youll_regret_that_4')

	actor.removeSkillMod('kill_meter_co_youll_regret_that_reac', 0)
	actor.removeSkillMod('expertise_youll_regret_that', 1000)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
